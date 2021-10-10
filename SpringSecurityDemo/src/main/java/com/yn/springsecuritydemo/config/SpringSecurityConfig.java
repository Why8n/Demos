package com.yn.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
                .roles("ADMIN") // ROLE_ADMIN
//                .authorities("ROLE_ADMIN","create", "read", "update", "delete")
                .build();

        // 普通用户
        UserDetails normalUser = User.builder()
                .username("user")
                .password(this.passwordEncoder.encode("user_password"))
                .roles("USER") // ROLE_USER
//                .authorities("read")
                .build();

        return new InMemoryUserDetailsManager(adminUser, normalUser);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
