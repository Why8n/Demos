package com.yn.springsecuritydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 所有授权请求都必须先进行认证
        http.authorizeRequests((request) -> request.anyRequest().authenticated());
        // 开启 HTTP Basic 认证
         http.httpBasic();
    }
}
