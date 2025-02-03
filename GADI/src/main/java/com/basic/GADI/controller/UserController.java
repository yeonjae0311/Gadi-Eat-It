package com.basic.GADI.controller;

import com.basic.GADI.config.JwtUtil;
import com.basic.GADI.dto.request.LoginRequestDto;
import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.TokenResponseDto;
import com.basic.GADI.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login (@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto));
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<TokenResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto)  {
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(registerRequestDto));
    }

    @GetMapping("/email/auth/{email}")
    public ResponseEntity<String> requestEmailAuth(@PathVariable("email") String email, HttpSession session) throws MessagingException {
        boolean isSend = userService.sendAuthMail(email, session);
        return ResponseEntity.ok().body(isSend ? "인증번호가 전송되었습니다." : "인증번호 전송에 실패하였습니다.");
    }

    @PostMapping("/email/auth")
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

}
