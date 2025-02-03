package com.basic.GADI.config;

import com.basic.GADI.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String signatureKey;

    @Value("${jwt.refresh-expiration}")
    private int refreshExpiration;

    public static SecretKey getSecretKey(String key) {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(key.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public String createAccessToken(User user) {
        return Jwts.builder()
                .claim("userEmail", user.getUserEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24L))
                .signWith(getSecretKey(signatureKey))
                .compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder()
                .claim("userEmail", user.getUserEmail())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSecretKey(signatureKey))
                .compact();
    }

    private Claims getClaimsToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey(signatureKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidRefreshToken(String refreshToken) {
        try {
            Claims claims = getClaimsToken(refreshToken);
            return true;
        } catch (NullPointerException | JwtException e) {
            return false;
        }
    }
}
