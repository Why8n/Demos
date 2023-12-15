package com.whyn.springsecuritydemo.filter;

import com.whyn.springsecuritydemo.service.IUserService;
import com.whyn.springsecuritydemo.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (null != jwtToken && jwtToken.startsWith(this.jwtTokenService.getTokenPrefix())) {
                // 解析 jwt token，失败抛异常
                Map<String, Object> userDetailsMap = this.jwtTokenService.parseToken(jwtToken);
                // 认证通过，从 token 中提取出 username
                String username = (String) userDetailsMap.get(JwtTokenService.KEY_USER_NAME);
                // 获取用户详细信息
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // 构建一个 Authentication 认证对象，填入用户相关信息
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username,     // principal 用户名
                        null, // credentials 密码敏感数据，直接置为空即可
                        userDetails.getAuthorities());
                // 附加详细信息，比如请求体，有些认证方式需要除了用户名密码外更多的信息
                // 后续可通过 Authentication#getDetails() 获取自定义的额外信息
                authentication.setDetails(request);
                // 认证成功，直接设置到 SecurityContextHolder 中，供后续 Filters 使用
                // 该操作会将 Authentication 存放到 ThreadLocal 中，这样当前请求在后续操作中就能获取到该 Authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}
