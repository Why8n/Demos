package com.whyn.springsecuritydemo.security;

import com.whyn.springsecuritydemo.entity.User;
import com.whyn.springsecuritydemo.filter.JwtTokenAuthenticationFilter;
import com.whyn.springsecuritydemo.service.IUserService;
import com.whyn.springsecuritydemo.service.JwtTokenService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final IUserService userService;
    private final JwtTokenService jwtTokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(this.authenticationManager())
                .addFilterBefore(new JwtTokenAuthenticationFilter(
                                this.jwtTokenService,
                                this.userDetailsService()),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(HttpMethod.POST, "/auth/signin", "/auth/signup").permitAll()
                            .requestMatchers("/test/**").permitAll()
                            .anyRequest().authenticated();
                });

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 获取用户及其相关详细信息
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userService.findUser(username);
                if (null == user) {
                    throw new UsernameNotFoundException(String.format("[username: %s] not found!", username));
                }
                return user;
            }
        };
    }

    // 负责具体用户认证流程
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // 创建一个用户认证提供者
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 将该 Provider 关联到我们设置的 UserDetailsService
        authProvider.setUserDetailsService(this.userDetailsService());
        // 关联到我们设置的加密算法
        authProvider.setPasswordEncoder(this.passwordEncoder());
        return authProvider;
    }

    // 认证管理者
    @Bean
    public AuthenticationManager authenticationManager() {
        // 关联到我们设置的 AuthenticationProvider
        return new ProviderManager(this.authenticationProvider());
    }
}
