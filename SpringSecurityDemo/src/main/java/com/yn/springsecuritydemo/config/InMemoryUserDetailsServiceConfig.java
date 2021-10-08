package com.yn.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
public class InMemoryUserDetailsServiceConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService createUserDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // 创建 admin 用户
        manager.createUser(
                User.withUsername("whyn")
                        .password(this.passwordEncoder.encode("123456"))
                        .roles("admin")
                        .authorities("create", "read", "update", "delete")
                        .build()
        );
        // 创建 tourist 用户
        manager.createUser(
                User.withUsername("tourist")
                        .password(this.passwordEncoder.encode("guest"))
                        .roles("tourist")
                        .build());
        return manager;
    }
}
