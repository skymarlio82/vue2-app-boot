
package com.wiz.app.vue2.boot.config;

import javax.annotation.Resource;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;

import com.wiz.app.vue2.boot.rest.filter.SessionFilter;

@Configuration
public class WebConfig {

	@Resource
    private RedisTemplate<String, Object> redisTemplate = null;

	@Bean
	public FilterRegistrationBean<SessionFilter> abcFilter() {
		FilterRegistrationBean<SessionFilter> filterRegBean = new FilterRegistrationBean<>();
		filterRegBean.setFilter(new SessionFilter(redisTemplate));
		filterRegBean.addUrlPatterns("/*");
		filterRegBean.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
		return filterRegBean;
	}
}