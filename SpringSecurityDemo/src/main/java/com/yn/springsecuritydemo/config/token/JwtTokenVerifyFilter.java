package com.yn.springsecuritydemo.config.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

// OncePerReequestFilter 可以确保单次请求只会执行一次 Filter
public class JwtTokenVerifyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (null != token
                && !"".equals(token)
                && token.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
            Map<String, Object> userDetails = JwtTokenUtils.parseToken(token);
            String username = (String) userDetails.get(JwtTokenUtils.KEY_USER_NAME);
            Collection<? extends GrantedAuthority> authorities =
                    (Collection<? extends GrantedAuthority>) userDetails.get(JwtTokenUtils.KEY_USER_AUTHORITIES);

            // 将 token 提取出来的用户信息封装到 Authentication 中
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,     // principal 用户名
                    null, // credentials 密码无法获取，直接置为空即可
                    authorities);
            // 认证成功，直接设置到 SecurityContextHolder 中，供后续 Filters 使用
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
