package com.basic.GADI.service;

import com.basic.GADI.dto.response.ResDetailResponseDto;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import com.basic.GADI.repository.ResRepository;
import com.basic.GADI.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResRepository resRepository;

    @Transactional
    public List<User> findUserList() {
        return userRepository.findAll();
    }

    @Transactional
    public List<Restaurants> findResList() {
        List<Restaurants> list = resRepository.findAll();
        System.out.println(list.isEmpty());
        return list;
    }

    @Transactional
    public ResDetailResponseDto findResDetail(Long resId) {
        Restaurants resDetail =  resRepository.findById(resId)
                .orElseThrow(()-> new BusinessException("해당하는 음식점을 찾을 수 없습니다."));
        return new ResDetailResponseDto().fromEntity(resDetail);
    }
}
