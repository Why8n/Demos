package com.yn.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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
    @Bean
    protected UserDetailsService userDetailsService() {
        // 管理员
        UserDetails adminUser = User.withUsername("admin")
                .password(this.passwordEncoder.encode("admin_password"))
                .roles()
                .authorities("create", "read", "update", "delete")
                .build();

        // 普通用户
        UserDetails normalUser = User.builder()
                .username("user")
                .password(this.passwordEncoder.encode("user_password"))
                .authorities("read")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return hierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭 CSRF
        http.csrf().disable()
                .authorizeRequests()
                // 拥有 create，或者 update，或者 delete 权限就可以访问 /admin/**
                .antMatchers("/admin/**").hasAnyAuthority("create", "update", "delete")
                // 只有拥有 read 权限才能访问：GET /user/**
                .antMatchers(HttpMethod.GET, "/user/**").hasAuthority("read")
                // 只有拥有 create 权限才能上传用户： POST /user/**
                .antMatchers(HttpMethod.POST,"/user/**").hasAuthority("create")
                // 只有拥有 update 权限才能更新用户： PUT /user/**
                .antMatchers(HttpMethod.PUT,"/user/**").hasAuthority("update")
                // 只有拥有 delete 权限才能删除用户： DELETE /user/**
                .antMatchers(HttpMethod.DELETE,"/user/**").hasAuthority("delete")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
