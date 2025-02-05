package com.basic.GADI.config;

import com.basic.GADI.entity.User;
import io.jsonwebtoken.*;
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
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2L))
                .signWith(getSecretKey(signatureKey))
                .compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder()
                .claim("userEmail", user.getUserEmail())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration * 1000 * 60 * 60 * 2L))
                .signWith(getSecretKey(signatureKey))
                .compact();
    }

    private Claims getClaimsToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey(signatureKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("❌ JWT 토큰이 만료되었습니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            System.out.println("❌ 지원되지 않는 JWT 형식입니다.");
            throw e;
        } catch (MalformedJwtException e) {
            System.out.println("❌ JWT 형식이 올바르지 않습니다.");
            throw e;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ JWT가 비어 있거나 잘못되었습니다.");
            throw e;
        }
    }

    public boolean isValidRefreshToken(String refreshToken) {
        try {
            Claims claims = getClaimsToken(refreshToken);
            System.out.println(claims);
            return true;
        } catch (NullPointerException | JwtException e) {
            return false;
        }
    }
}
