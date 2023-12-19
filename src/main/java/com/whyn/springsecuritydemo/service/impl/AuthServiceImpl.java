package com.whyn.springsecuritydemo.service.impl;

import com.whyn.springsecuritydemo.entity.User;
import com.whyn.springsecuritydemo.mapper.IUserDao;
import com.whyn.springsecuritydemo.service.IAuthService;
import com.whyn.springsecuritydemo.service.JwtTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserDao userDao;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean signUp(User user) {
        String encodePassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        return this.userDao.insert(user) > 0;
    }

    @Override
    public String signIn(User user) {
        String jwtToken = null;
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
                throw new AuthenticationServiceException("username & password must not be null");
            }
            // 构造一个 Authentication 对象，设置 principal & credentials
            // principal 意为主要的，对应数据库中唯一字段(unique key)，此处设置为 username，
            // 若进行更改，则相应的 userDetails#getUsername() 和 UserDetailsService#loadUserByUsername(String username)
            // 都要设置为对同一字段进行操作
            // credentials 就是指代密码
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            // 可自定义填充其余信息，方便后续获取用户时，能获取这些自定义信息
            // ((UsernamePasswordAuthenticationToken)authentication).setDetails(null);

            // AuthenticationManager 进行认证，认证失败抛异常
            // 认证通过，成功返回一个新的 Authentication 对象，其内包含有用户所有信息(包含上面自定义信息 setDetails），只是将密码去除
            Authentication successDetailedAuth = this.authManager.authenticate(authentication);
            // 认证通过
            // 获取用户详细信息
            Collection<? extends GrantedAuthority> authorities = successDetailedAuth.getAuthorities();
            // 下发 jwt token
            jwtToken = this.jwtTokenService.generateToken(username, authorities);

        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return jwtToken;
    }
}
