package com.yn.springsecuritydemo.config.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JwtTokenUtils {
    // 私钥
    private static final String PRIVATE_SECRET_KEY = "this_is_private_secret_key";
    // token 默认过期时间：14 天
    private static final long TOKEN_EXPIRATION = TimeUnit.DAYS.toMillis(14);
    // 下发 token 增加前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String KEY_USER_NAME = "username";
    public static final String KEY_USER_AUTHORITIES = "authorities";

    // 解析 token
    public static Map<String, Object> parseToken(String token) {
        Map<String, Object> userDetails = new HashMap<>();
        try {
            token = validateToken(token);
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey()).build().parseClaimsJws(token);
            // 用户名
            String username = claimsJws.getBody().getSubject();
            // 用户权限
            List<Map<String, String>> authorities = (List<Map<String, String>>) claimsJws.getBody().get(KEY_USER_AUTHORITIES);
            Collection<? extends GrantedAuthority> userAuthorities = authorities.stream()
                    .map(item ->
                            new SimpleGrantedAuthority(item.get("authority")))
                    .collect(Collectors.toSet());
            userDetails.put(KEY_USER_NAME, username);
            userDetails.put(KEY_USER_AUTHORITIES, userAuthorities);
            return userDetails;
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("invalid token: %s", token));
        }
    }

    // 生成token
    public static String generateToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(getSecretKey())
                .compact();
        return generateTokenWithPrefix(token);
    }

    // 生成 token，包含用户主体和其权限
    public static String generateToken(String username, Object authorities) {
        String token = Jwts.builder()
                // 用户名
                .setSubject(username)
                // payload
                .claim(KEY_USER_AUTHORITIES, authorities)
                // 发行时间
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                // 私钥
                .signWith(getSecretKey())
                .compact();
        return generateTokenWithPrefix(token);
    }

    // token 添加前缀 Bearer
    private static String generateTokenWithPrefix(final String token) {
        return TOKEN_PREFIX + token;
    }

    // 生成签名私钥
    private static Key getSecretKey() {
        return Keys.hmacShaKeyFor(generateSecretKey());
    }

    // 加密要求至少 256 位，因此将私钥进行 sha256，只是单纯为了生存 256 个字节
    private static byte[] generateSecretKey() {
        byte[] hashKey = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hashKey = digest.digest(PRIVATE_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            hashKey = fillBytes(PRIVATE_SECRET_KEY);
        }
        return hashKey;
    }

    // 循环字符串添加到 256 个字节
    private static byte[] fillBytes(String str) {
        if (str == null) {
            str = PRIVATE_SECRET_KEY;
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
    private static String validateToken(String token) {
        String rawToken = token;
        if (rawToken.startsWith(TOKEN_PREFIX)) {
            rawToken = rawToken.substring(TOKEN_PREFIX.length());
        }
        return rawToken;
    }
}
