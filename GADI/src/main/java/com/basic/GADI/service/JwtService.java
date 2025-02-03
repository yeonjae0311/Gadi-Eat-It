package com.basic.GADI.service;

import com.basic.GADI.dto.request.LoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String signatureKey;

    @Value("${jwt.refresh-expiration}")
    private String refreshExpiration;

    public static SecretKey getSecretKey(String key) {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(key.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public String createAccessToken(LoginDto loginDto) {
        return Jwts.builder()
                .claim("userEmail", loginDto.getUserEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24L))
                .signWith(getSecretKey(signatureKey))
                .compact();
    }

    public String createRefreshToken(LoginDto loginDto) {
        return Jwts.builder()
                .claim("userEmail", loginDto.getUserEmail())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSecretKey(signatureKey))
                .compact();
    }
}
