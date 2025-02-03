package com.basic.GADI.controller;

import com.basic.GADI.config.JwtUtil;
import com.basic.GADI.dto.request.LoginRequestDto;
import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.TokenResponseDto;
import com.basic.GADI.service.UserService;
import jakarta.mail.MessagingException;
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
    public ResponseEntity<String> requestEmailAuth(@PathVariable("email") String email) throws MessagingException {
        boolean isSend = userService.sendAuthMail(email);
        return ResponseEntity.ok().body(isSend ? "인증번호가 전송되었습니다." : "인증번호 전송에 실패하였습니다.");
    }
}
