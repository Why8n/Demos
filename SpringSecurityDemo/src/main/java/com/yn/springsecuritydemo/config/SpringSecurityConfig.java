package com.yn.springsecuritydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Spring Security 自定义配置类
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 放行接口 login/**，/test/index
                .antMatchers("/login/**","/test/index").permitAll()
                // 其余所有请求都必须经过认证
                .anyRequest().authenticated()
                .and()
                // 使用 HTTP Basic 认证
                .httpBasic()
                .and()
                // 同时支持表单认证（网页端优先使用表单认证）
                .formLogin();
    }
}
