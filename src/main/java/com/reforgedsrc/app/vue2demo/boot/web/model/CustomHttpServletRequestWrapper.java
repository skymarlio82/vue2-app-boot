package com.reforgedsrc.app.vue2demo.boot.web.model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.RedisTemplate;

import com.reforgedsrc.app.vue2demo.boot.constant.WebConstant;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private CacheHttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @SuppressWarnings("rawtypes")
    private RedisTemplate redisTemplate;
    private String localhost;

    @SuppressWarnings("rawtypes")
    public CustomHttpServletRequestWrapper(
        HttpServletRequest request, HttpServletResponse response, RedisTemplate redisTemplate) {
        super(request);
        localhost = request.getLocalName();
        this.request = request;
        this.response = response;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public HttpSession getSession(boolean create) {
        String head_auth = request.getHeader(WebConstant.JWT_HTTP_HEAD_AUTHOR);
        if (head_auth == null) {
            return request.getSession();
        } else {
            String token = head_auth.substring(7);
            int idx_start = token.lastIndexOf(".") + 1;
            // separate the check_sum from token as sessionId
            String sid = token.substring(idx_start, token.length());
            writeSidToCookie(sid);
            session = new CacheHttpSession(request.getSession(), sid, redisTemplate);
            return session;
        }
    }

    @Override
    public HttpSession getSession() {
        return getSession(false);
    }

    protected void writeSidToCookie(String sid) {
        Cookie cookie = new Cookie("session_id", sid);
        cookie.setMaxAge(-1);
        cookie.setDomain(localhost);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}