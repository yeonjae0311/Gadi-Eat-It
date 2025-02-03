package com.basic.GADI.service;

import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.RegisterResponseDto;
import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import com.basic.GADI.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto)  {
        if (userRepository.existsByUserEmail(registerRequestDto.getUserEmail())) {
            throw new BusinessException("이미 존재하는 아이디입니다.", HttpStatus.NOT_FOUND);
        }

        User registerUser = User.builder()
                .userEmail(registerRequestDto.getUserEmail())
                .userName(registerRequestDto.getUserName())
                .userPw(passwordEncoder.encode(registerRequestDto.getUserPw()))
                .build();

        userRepository.save(registerUser);

        return new RegisterResponseDto(registerRequestDto.getUserEmail(), registerRequestDto.getUserName());
    }
}
