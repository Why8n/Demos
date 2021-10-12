package com.yn.springsecuritydemo.config;

import com.yn.springsecuritydemo.config.token.JwtTokenUsernamePasswordAuthenticationFilter;
import com.yn.springsecuritydemo.config.token.JwtTokenUtils;
import com.yn.springsecuritydemo.config.token.JwtTokenVerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        http.authorizeRequests(request -> request.anyRequest().authenticated())
                // 关闭 CSRF 预防
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManager ->
                        // 无需创建 Session
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 添加自定义 token 认证过滤器（下发 token）
                .addFilter(new JwtTokenUsernamePasswordAuthenticationFilter(
                        this.authenticationManager()))
                // 添加自定义 token 提取过滤器（提取并认证）
                .addFilterAfter(new JwtTokenVerifyFilter(),
                        JwtTokenUsernamePasswordAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
