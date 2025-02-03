package com.basic.GADI.controller;

import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.RegisterResponseDto;
import com.basic.GADI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto registerRequestDto)  {
        RegisterResponseDto register = userService.register(registerRequestDto);
        return ResponseEntity.ok().body(register);
    }
}
