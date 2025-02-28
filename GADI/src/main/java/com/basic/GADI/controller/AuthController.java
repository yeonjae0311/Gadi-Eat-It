
package com.basic.GADI.controller;

import com.basic.GADI.config.jwt.JwtUtil;
import com.basic.GADI.dto.request.LoginRequestDto;
import com.basic.GADI.dto.request.PasswordLinkRequestDto;
import com.basic.GADI.dto.request.PasswordResetRequestDto;
import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.TokenResponseDto;
import com.basic.GADI.entity.User;
import com.basic.GADI.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login (@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
    }

    @PostMapping("/refreshLogin")
    public ResponseEntity<TokenResponseDto> login (HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println(authorizationHeader);
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // "Bearer " 뒤에 오는 토큰 부분만 추출
            token = authorizationHeader.substring(7);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshLogin(token));
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto registerRequestDto)  {
        authService.register(registerRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/send/email")
    public ResponseEntity<String> sendEmailAuth(@RequestBody Map<String, String> requestData, HttpSession session) throws MessagingException {
        String email = requestData.get("email");
        boolean isSend = authService.sendAuthMail(email, session);
        return ResponseEntity.ok().body(isSend ? "인증번호가 전송되었습니다." : "인증번호 전송에 실패하였습니다.");
    }

    @PostMapping("/verify/email")
    public ResponseEntity<String> verifyEmailAuth(@RequestBody Map<String, String> requestData, HttpSession session) {
        String email = requestData.get("email");
        String inputAuthCode = requestData.get("inputAuthCode");

        String authCode = (String)session.getAttribute("authCode");
        String sendEmail = (String)session.getAttribute("sendEmail");

        if (authCode != null && authCode.equals(inputAuthCode) && email.equals(sendEmail)) {
            session.removeAttribute("authCode");
            session.removeAttribute("sendEmail");
            return ResponseEntity.ok().body("인증이 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("인증에 실패하였습니다.다시 인증해주세요.");
        }
    }

    @PostMapping("send/email/password_link")
    public ResponseEntity<String> requestResetPasswordLink(@RequestBody @Valid PasswordLinkRequestDto passwordLinkRequestDto) throws MessagingException {
        String sendEmail = passwordLinkRequestDto.getUserEmail();
        System.out.println(sendEmail);
        Optional<User> user = authService.findByEmail(sendEmail);

        if (user.isPresent()) {
            Long userId = user.get().getUserId();
            String tokenLink = authService.sendPasswordMail(sendEmail, userId);
            return ResponseEntity.ok(tokenLink);
        } else {
            return ResponseEntity.badRequest().body("가입되지 않은 이메일입니다.");
        }
    }

    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordResetRequestDto passwordResetRequestDto)  {

        String newPassword = passwordResetRequestDto.getUserPw();

        return ResponseEntity.ok().body("비밀번호가 재설정되었습니다.");
    }
}
