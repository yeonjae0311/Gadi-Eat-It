package com.basic.GADI.service;

import com.basic.GADI.dto.request.MyInfoRequestDto;
import com.basic.GADI.dto.response.MyInfoResponseDto;
import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import com.basic.GADI.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    public MyInfoResponseDto findMyInfo(String userEmail) {
        Optional<User> findOneUser = userRepository.findByUserEmail(userEmail);
        if (findOneUser.isEmpty()) {
            throw new BusinessException("해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        return MyInfoResponseDto.fromUser(findOneUser.get());
    }

    @Transactional
    public void updateMyInfo(String userEmail, MyInfoRequestDto requestDto) {
        Optional<User> findOneUser = userRepository.findByUserEmail(userEmail);
        if (findOneUser.isPresent()) {
            User user = findOneUser.get();
            user.updateMyInfo(
                    requestDto.getUserName(),
                    requestDto.getUserFile(),
                    requestDto.getUserPhone(),
                    requestDto.getUserBirth()
            );
        } else {
            throw new BusinessException("해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }
}
