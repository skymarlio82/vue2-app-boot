package com.reforgedsrc.app.vue2demo.boot.config;

import javax.annotation.Resource;

import com.reforgedsrc.app.vue2demo.boot.web.filter.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class WebConfig {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public FilterRegistrationBean<SessionFilter> sessionFilter() {
        FilterRegistrationBean<SessionFilter> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new SessionFilter(redisTemplate));
        filterRegBean.addUrlPatterns("/*");
        filterRegBean.setOrder(Ordered.LOWEST_PRECEDENCE - 1);
        return filterRegBean;
    }
}