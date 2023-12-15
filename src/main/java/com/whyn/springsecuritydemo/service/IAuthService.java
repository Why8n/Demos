package com.whyn.springsecuritydemo.service;

import com.whyn.springsecuritydemo.entity.User;
import org.springframework.security.core.AuthenticationException;

public interface IAuthService {

    boolean signUp(User user);

    String signIn(User user);
}
