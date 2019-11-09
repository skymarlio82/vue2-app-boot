
package com.reforgedsrc.app.vue2demo.boot.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;

import com.reforgedsrc.app.vue2demo.boot.web.model.CustomHttpServletRequestWrapper;

public class SessionFilter implements Filter {

	private RedisTemplate<String, Object> redisTemplate = null;

	public SessionFilter(RedisTemplate<String, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		CustomHttpServletRequestWrapper wrapper = new CustomHttpServletRequestWrapper(req, res, redisTemplate);
		chain.doFilter(wrapper, response);
	}

	@Override
	public void destroy() {
		
	}
}