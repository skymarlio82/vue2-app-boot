package com.reforgedsrc.app.vue2demo.boot.config;

import com.reforgedsrc.app.vue2demo.boot.constant.WebConstant;
import com.reforgedsrc.app.vue2demo.boot.security.model.JwtAuthenticationEntryPoint;
import com.reforgedsrc.app.vue2demo.boot.security.model.JwtAuthorizationTokenFilter;
import com.reforgedsrc.app.vue2demo.boot.security.model.JwtTokenUtil;
import com.reforgedsrc.app.vue2demo.boot.security.service.JwtUserDetailsService;
import com.reforgedsrc.app.vue2demo.boot.util.SimpleMd5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    public WebSecurityConfig(
        JwtAuthenticationEntryPoint unauthorizedHandler,
        JwtTokenUtil jwtTokenUtil,
        JwtUserDetailsService jwtUserDetailsService) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new SimpleMd5PasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // we don't need CSRF because our token is invulnerable
            .csrf().disable()
            .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
            .and()
            // don't create session
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // Unsecured H2 Database
                .antMatchers("/h2-console/**/**").permitAll()
                .antMatchers("/auth/**").permitAll()
            .anyRequest()
                .authenticated();
        // Custom JWT based security filter
        http.addFilterBefore(
            new JwtAuthorizationTokenFilter(userDetailsService(), jwtTokenUtil, WebConstant.JWT_HTTP_HEAD_AUTHOR),
            UsernamePasswordAuthenticationFilter.class);
        // disable page caching
        http
            .headers()
            .frameOptions()
            // required to set for H2 else H2 Console will be blank.
            .sameOrigin()
            .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // AuthenticationTokenFilter will ignore the below paths
        web
            .ignoring()
                .antMatchers(HttpMethod.POST, WebConstant.JWT_ROUTE_AUTHEN_PATH, WebConstant.JWT_ROUTE_AUTHEN_REFRESH)
            .and()
            // allow anonymous resource requests
            .ignoring()
                .antMatchers(HttpMethod.GET,
                    "/",
                    "/*.html",
                    "/**/*.css",
                    "/**/*.js",
                    "/favicon.ico",
                    "/**/*.gif",
                    "/**/*.jpg",
                    "/**/*.png")
            .and()
            // Unsecured H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
            .ignoring()
                .antMatchers("/h2-console/**/**");
    }
}