package com.basic.GADI.service;

import com.basic.GADI.config.JwtUtil;
import com.basic.GADI.dto.request.LoginRequestDto;
import com.basic.GADI.dto.request.RegisterRequestDto;
import com.basic.GADI.dto.response.TokenResponseDto;
import com.basic.GADI.entity.RefreshToken;
import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import com.basic.GADI.repository.RefreshTokenRepository;
import com.basic.GADI.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public TokenResponseDto register(RegisterRequestDto registerRequestDto)  {
        if (userRepository.existsByUserEmail(registerRequestDto.getUserEmail())) {
            throw new BusinessException("이미 존재하는 아이디입니다.", HttpStatus.NOT_FOUND);
        }

        User registerUser = User.builder()
                .userEmail(registerRequestDto.getUserEmail())
                .userName(registerRequestDto.getUserName())
                .userPw(passwordEncoder.encode(registerRequestDto.getUserPw()))
                .build();

        userRepository.save(registerUser);
        String accessToken = jwtUtil.createAccessToken(registerUser);
        String refreshToken = jwtUtil.createRefreshToken(registerUser);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .user(registerUser)
                        .token(refreshToken)
                        .build()
        );

        return TokenResponseDto.builder()
                .AccessToken(accessToken)
                .RefreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUserEmail(loginRequestDto.getUserEmail())
                .orElseThrow(() -> new BusinessException("존재하지 않는 아이디입니다.", HttpStatus.NOT_FOUND));

        if (!new BCryptPasswordEncoder().matches(loginRequestDto.getUserPw(), user.getUserPw())) {
            throw new BusinessException("비밀번호가 일치하지 않습니다.");
        }

        RefreshToken existingRefreshToken = refreshTokenRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new BusinessException("토큰이 존재하지 않습니다."));

        String accessToken = "";
        String refreshToken = existingRefreshToken.getRefreshToken();

        if(jwtUtil.isValidRefreshToken(refreshToken)) {
            accessToken = jwtUtil.createAccessToken(user);
            return TokenResponseDto.builder()
                    .AccessToken(accessToken)
                    .RefreshToken(refreshToken)
                    .build();
        } else {
            refreshToken = jwtUtil.createRefreshToken(user);
            existingRefreshToken.updateToken(refreshToken);
        }
        return TokenResponseDto.builder()
                .AccessToken(accessToken)
                .RefreshToken(refreshToken)
                .build();
    }
}
