package com.yn.springsecuritydemo.config.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JwtTokenUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtTokenUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Authentication auth = null;
        try {
            // 获取请求参数
            Map<String, String> userMap = new ObjectMapper().readValue(
                    request.getInputStream(), Map.class);
            // 获取用户名
            String username = userMap.get(this.getUsernameParameter());
            // 获取用户密码
            String password = userMap.get(this.getPasswordParameter());
            // 包装为一个 Authentication 对象，方便后面给 AuthenticationManager 进行验证
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
            // 进行验证
            auth = this.authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return auth;
    }

    @Override // 认证成功
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 生成 token
        String token = JwtTokenUtils.generateToken(authResult.getName(), authResult.getAuthorities());
        // 下发 token
        response.addHeader("Authorization", token);
    }

    @Override // 认证失败
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
