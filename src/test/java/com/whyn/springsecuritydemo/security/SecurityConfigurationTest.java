package com.whyn.springsecuritydemo.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecurityConfigurationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoder(){
        String adminPassword = this.passwordEncoder.encode("admin_password");
        System.out.println(adminPassword);

        String userPassword = this.passwordEncoder.encode("whyn_password");
        System.out.println(userPassword);
    }

}