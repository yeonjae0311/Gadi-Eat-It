package com.basic.GADI.config.jwt;

import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
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
                .claim("userEmail", user.getUserEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2L))
                .signWith(Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String createRefreshToken(User user) {
        return Jwts.builder()
                .claim("userEmail", user.getUserEmail())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration * 1000 * 60 * 60 * 2L))
                .signWith(Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    private Claims getClaimsToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
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
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
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
        final String username = extractUsername(token);
        //UserDetails 포함된 email 주소와 토큰에 포함된 email 주소 비교
        return (username.equals(user.getUserEmail()) && !isTokenExpired(token));
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
            throw new BusinessException(
                    "JWT was exprired or incorrect");
        }
    }

}
