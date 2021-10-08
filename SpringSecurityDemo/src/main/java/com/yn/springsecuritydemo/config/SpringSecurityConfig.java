package com.yn.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Spring Security 自定义配置类
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**",
                "/fonts/**", "/favicon.ico", "/verifyCode");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String password = this.passwordEncoder.encode("123456");
        // 内存用户
        auth.inMemoryAuthentication()
                // 用户名
                .withUser("whyn")
                // 密码
                .password(password)
                // 角色
                .roles("admin")
                // 权限 CRUD
                .authorities("create","read","udpate","delete");
    }
}
