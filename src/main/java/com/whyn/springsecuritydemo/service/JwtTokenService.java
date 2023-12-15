package com.whyn.springsecuritydemo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_USER_AUTHORITIES = "authorities";

    @Value("${jwt.token.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expiration}")
    private long expiration;
    @Value("${jwt.token.token-prefix}")
    private String tokenPrefix;

    public String getTokenPrefix() {
        return this.tokenPrefix;
    }

    // 解析 token
    public Map<String, Object> parseToken(String token) {
        Map<String, Object> userDetails = new HashMap<>();
        try {
            token = validateToken(token);
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(this.getSecretKey()).build().parseClaimsJws(token);
            // 用户名
            String username = claimsJws.getBody().getSubject();
            userDetails.put(KEY_USER_NAME, username);
            // 用户权限
            List<Map<String, String>> authorities = (List<Map<String, String>>) claimsJws.getBody().get(KEY_USER_AUTHORITIES);
            if (null != authorities) {
                Collection<? extends GrantedAuthority> userAuthorities = authorities.stream()
                        .map(item ->
                                new SimpleGrantedAuthority(item.get("authority")))
                        .collect(Collectors.toSet());
                userDetails.put(KEY_USER_AUTHORITIES, userAuthorities);
            }
            return userDetails;
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("invalid token: %s", token));
        }
    }

    // 生成token
    public String generateToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(this.getSecretKey())
                .compact();
        return generateTokenWithPrefix(token);
    }

    // 生成 token，包含用户主体和其权限
    public String generateToken(String username, Object authorities) {
        String token = Jwts.builder()
                // 用户名
                .setSubject(username)
                // payload
                .claim(KEY_USER_AUTHORITIES, authorities)
                // 发行时间
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                // 私钥
                .signWith(getSecretKey())
                .compact();
        return generateTokenWithPrefix(token);
    }

    // token 添加前缀 Bearer
    private String generateTokenWithPrefix(final String token) {
        return String.format("%s %s", this.tokenPrefix, token);
    }

    // 生成签名私钥
    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(generateSecretKey());
    }

    // 加密要求至少 256 位，因此将私钥进行 sha256，只是单纯为了生存 256 个字节
    private byte[] generateSecretKey() {
        byte[] hashKey = null;
        String secretKey = this.secretKey;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hashKey = digest.digest(secretKey.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            hashKey = this.fillBytes(secretKey);
        }
        return hashKey;
    }

    // 循环字符串添加到 256 个字节
    private byte[] fillBytes(String str) {
        if (str == null) {
            throw new IllegalArgumentException("secret key must not be null!");
        }
        byte[] bytes256 = new byte[256];
        int length = str.length();
        for (int i = 0; i < 256; ++i) {
            // 忽视精度缺失，只是为了添加到 256 个字节
            bytes256[i] = (byte) str.charAt(i % length);
        }
        return bytes256;
    }

    // 去除 token 前缀：Bearer
    private String validateToken(String token) {
        String rawToken = token;
        String tokenPrefix = this.tokenPrefix;
        if (rawToken.startsWith(tokenPrefix)) {
            rawToken = rawToken.substring(tokenPrefix.length()).trim();
        }
        return rawToken;
    }
}
