package com.basic.GADI.config.jwt;

import com.basic.GADI.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String signatureKey;

    @Value("${jwt.refresh-expiration}")
    private int refreshExpiration;

    public String createAccessToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2L))
                .signWith(Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getUserId())
                .claim("role", user.getRole())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration * 1000 * 60 * 60 * 2L))
                .signWith(Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    //JWT 토큰에 저장된 정보 추출
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //PayLoad의 "sub": "user@aa.com", subject에 매핑된 Email 주소 추출하기
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //JWT 토큰 검증
    public Boolean validateToken(String token, User user) {
        //토큰에 포함된 email 주소 추출
        final Long userId = extractUserId(token);
        //UserDetails 포함된 email 주소와 토큰에 포함된 email 주소 비교
        return (userId.equals(user.getUserId()) && !isTokenExpired(token));
    }

    //JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}