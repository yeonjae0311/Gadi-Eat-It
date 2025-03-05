package com.basic.GADI.service;

import com.basic.GADI.config.jwt.JwtUtil;
import com.basic.GADI.dto.request.LoginRequestDto;
import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.TokenResponseDto;
import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import com.basic.GADI.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final JwtUtil jwtUtil;

    @Value("{spring.mail.username}")
    private static String senderEmail;

    @Value("${jwt.secretKey}")
    private String signatureKey;

    @Transactional
    public void register(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByUserEmail(registerRequestDto.getUserEmail())) {
            throw new BusinessException("이미 존재하는 아이디입니다.", HttpStatus.NOT_FOUND);
        }
        String encodedPw = passwordEncoder.encode(registerRequestDto.getUserPw());

        User registerUser = registerRequestDto.toEntity(encodedPw);

        userRepository.save(registerUser);
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUserEmail(loginRequestDto.getUserEmail())
                .orElseThrow(() -> new BusinessException("존재하지 않는 아이디입니다.", HttpStatus.NOT_FOUND));

        if (!new BCryptPasswordEncoder().matches(loginRequestDto.getUserPw(), user.getUserPw())) {
            throw new BusinessException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = "";
        String refreshToken = "";

        accessToken = jwtUtil.createAccessToken(user);
        refreshToken = jwtUtil.createRefreshToken(user);

        return TokenResponseDto.builder()
                .AccessToken(accessToken)
                .RefreshToken(refreshToken)
                .build();
    }

    public String createAuthCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            if (random.nextBoolean()) {
                key.append((char) (random.nextInt(26) + 65));
            } else {
                key.append(random.nextInt(10));
            }
        }
        return key.toString();
    }


    public MimeMessage createAuthMail(String mail, String authCode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        mimeMessage.setFrom(senderEmail);
        mimeMessage.setRecipients(MimeMessage.RecipientType.TO, mail);
        mimeMessage.setSubject("[Gadi-Eat-It] 이메일 인증");
        String body = "";
        body += "<h3>안녕하세요. Gadi Eat IT! 입니다.</h3> ";
        body += "<h3><span>요청하신 인증번호는 </span><span>" + authCode + "</span><span>입니다.</span></h3>";
        body += "<h3>감사합니다.</h3>";
        mimeMessage.setText(body, "UTF-8", "html");

        return mimeMessage;
    }


    public boolean sendAuthMail(String sendEmail, HttpSession session) throws MessagingException {
        String authCode = createAuthCode();
        MimeMessage createMail = createAuthMail(sendEmail, authCode);
        try {
            javaMailSender.send(createMail);
            session.setAttribute("sendEmail", sendEmail);
            session.setAttribute("authCode", authCode);
            session.setMaxInactiveInterval(180);
            System.out.println(session.getAttribute("sendEmail"));
            System.out.println(session.getAttribute("authCode"));
            return true;
        } catch (MailException e) {
            return false;
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    public String sendPasswordMail(String sendEmail, Long userId) throws MessagingException {
        String link = createResetPasswordLink(sendEmail, userId);
        MimeMessage createMail = createResetPasswordMail(link, sendEmail);
        javaMailSender.send(createMail);
        return link;
    }

    private MimeMessage createResetPasswordMail(String link, String sendEmail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(senderEmail);
        mimeMessage.setRecipients(MimeMessage.RecipientType.TO, String.valueOf(new InternetAddress(sendEmail)));
        mimeMessage.setSubject("[Gadi-Eat-It] 비밀번호 재설정 링크입니다.");
        String body = "";
        body += "<h3>안녕하세요. Gadi Eat IT! 입니다.</h3> ";
        body += "<h3>비밀번호 재설정 링크입니다.</h3> ";
        body += "<h3><span>" + link + "</span></h3>";
        body += "<h3>감사합니다.</h3>";
        mimeMessage.setText(body, "UTF-8", "html");

        return mimeMessage;

    }

    private String createResetPasswordLink(String sendEmail, Long userId) {
        Duration tokenTime = Duration.ofMinutes(5);
        String linkToken = createLinkToken(sendEmail, userId, tokenTime);

        return "http://localhost:5173/resetPw?token=" + linkToken + "&email=" + sendEmail;
    }

    private String createLinkToken(String sendEmail, Long userId, Duration tokenTime) {

        return Jwts.builder()
                .claim("sendEmail", sendEmail)
                .claim("userId", userId)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(tokenTime)))
                .signWith(Keys.hmacShaKeyFor(signatureKey.getBytes()))
                .compact();
    }

    @Transactional
    public void resetUserPw(String userEmail, String newPassword) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(()-> new BusinessException("해당 사용자를 찾을 수 없습니다."));
        user.resetUserPw(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public TokenResponseDto refreshLogin(String token) {
        System.out.println(token);
        if (jwtUtil.validateToken(token)) {
            Long userId = jwtUtil.extractUserId(token);
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new BusinessException("사용자를 찾을 수 없습니다."));
            String accessToken = jwtUtil.createAccessToken(user);
            String refreshToken = jwtUtil.createRefreshToken(user);

            return TokenResponseDto.builder()
                    .AccessToken(accessToken)
                    .RefreshToken(refreshToken)
                    .build();
        } else {
            throw new BusinessException("유효하지 않은 토큰입니다.", HttpStatus.FORBIDDEN);
        }
    }
}
