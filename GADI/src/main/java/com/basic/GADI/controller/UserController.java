package com.basic.GADI.controller;

import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.RegisterResponseDto;
import com.basic.GADI.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import com.basic.GADI.dto.request.LoginDto;
import com.basic.GADI.dto.response.TokenResponseDto;
import com.basic.GADI.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login (@RequestBody LoginDto loginDto) {
        String jwt = jwtService.createAccessToken(loginDto);
        return ResponseEntity.status(HttpStatus.OK).header(jwt).build();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto)  {
        RegisterResponseDto register = userService.register(registerRequestDto);
        return ResponseEntity.ok().body(register);
    }

    @GetMapping("/email/auth/{email}")
    public ResponseEntity<String> requestEmailAuth(@PathVariable("email") String email) throws MessagingException {
        boolean isSend = userService.sendAuthMail(email);
        return ResponseEntity.ok().body(isSend ? "인증번호가 전송되었습니다." : "인증번호 전송에 실패하였습니다.");
    }
}
