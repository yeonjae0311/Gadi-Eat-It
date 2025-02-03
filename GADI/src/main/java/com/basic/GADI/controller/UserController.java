package com.basic.GADI.controller;

import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.RegisterResponseDto;
import com.basic.GADI.service.UserService;
import jakarta.validation.Valid;
import com.basic.GADI.dto.request.LoginDto;
import com.basic.GADI.dto.response.TokenResponseDto;
import com.basic.GADI.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login (@RequestBody LoginDto loginDto) {

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto)  {
        RegisterResponseDto register = userService.register(registerRequestDto);
        return ResponseEntity.ok().body(register);
        String jwt = jwtService.createAccessToken(loginDto);

        return ResponseEntity.status(HttpStatus.OK).header(jwt).build();
    }
}
