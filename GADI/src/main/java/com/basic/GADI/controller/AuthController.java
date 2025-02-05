
package com.basic.GADI.controller;

import com.basic.GADI.dto.request.LoginRequestDto;
import com.basic.GADI.dto.request.PasswordLinkRequestDto;
import com.basic.GADI.dto.request.PasswordResetRequestDto;
import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.TokenResponseDto;
import com.basic.GADI.entity.User;
import com.basic.GADI.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login (@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<TokenResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto)  {
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(registerRequestDto));
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> requestEmailAuth(@PathVariable("email") String email, HttpSession session) throws MessagingException {
        boolean isSend = authService.sendAuthMail(email, session);
        return ResponseEntity.ok().body(isSend ? "인증번호가 전송되었습니다." : "인증번호 전송에 실패하였습니다.");
    }

    @PostMapping("/email")
    public ResponseEntity<String> verifyEmailAuth(@RequestParam String email,
                                                  @RequestParam String inputAuthCode, HttpSession session) {
        String authCode = (String)session.getAttribute(email);
        if (authCode != null && authCode.equals(inputAuthCode)) {
            session.removeAttribute(email);
            return ResponseEntity.ok().body("인증이 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("인증에 실패하였습니다.다시 인증해주세요.");
        }
    }

    @PostMapping("/email/password_link")
    public ResponseEntity<String> requestResetPasswordLink(@RequestBody @Valid PasswordLinkRequestDto passwordLinkRequestDto) throws MessagingException {
        String sendEmail = passwordLinkRequestDto.getUserEmail();
        Optional<User> user = authService.findByEmail(sendEmail);

        if (user.isPresent()) {
            Long userId = user.get().getUserId();
            String tokenLink = authService.sendPasswordMail(sendEmail, userId);
            return ResponseEntity.ok(tokenLink);
        } else {
            return ResponseEntity.badRequest().body("가입되지 않은 이메일입니다.");
        }
    }

    @PostMapping("/reset/password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordResetRequestDto passwordResetRequestDto)  {
        String token = passwordResetRequestDto.getToken();
        String newPassword = passwordResetRequestDto.getUserPw();

        if (!authService.isValid(token)) {
            return ResponseEntity.badRequest().body("토큰이 유효하지 않거나 만료되었습니다.");
        }

        authService.updateUserPw(passwordResetRequestDto.getUserEmail(), newPassword);
        return ResponseEntity.ok().body("비밀번호가 재설정되었습니다.");
    }
}
