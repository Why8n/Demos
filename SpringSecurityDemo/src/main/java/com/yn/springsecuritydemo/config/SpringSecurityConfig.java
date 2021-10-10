package com.yn.springsecuritydemo.config;

import com.yn.springsecuritydemo.config.jump.AuthenticationFailureHandlerImpl;
import com.yn.springsecuritydemo.config.jump.AuthenticationSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

// Spring Security 自定义配置类
@Component
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
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .and()
                .logout(logout -> {
                    // doLogout 无需自己定义，可直接访问退出
                    logout.logoutUrl("/doLogout")
//                            logout.logoutRequestMatcher(new AntPathRequestMatcher("/doLogout","POST"));
                            // 退出成功后跳转页面
                            .logoutSuccessUrl("/test/index").permitAll()
                            // 清除 cookie
                            .deleteCookies()
                            // 清除认证消息 (默认使能）
                            .clearAuthentication(true)
                            // 清除 Session（默认使能）
                            .invalidateHttpSession(true);
                });
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
