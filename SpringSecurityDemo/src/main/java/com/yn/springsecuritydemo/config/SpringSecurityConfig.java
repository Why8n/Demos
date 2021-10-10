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

import java.util.concurrent.TimeUnit;

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
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .rememberMe(rememberMe -> {
                    // 用于生成 token 的密钥
                    rememberMe.key("uniqueAndSecure")
                            // Cookie 名称
                            .rememberMeCookieName("remember")
                            .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(2));
                });
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
