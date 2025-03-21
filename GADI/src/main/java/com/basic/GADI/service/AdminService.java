package com.basic.GADI.service;

import com.basic.GADI.dto.response.PageResponseDto;
import com.basic.GADI.dto.response.ResDetailResponseDto;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import com.basic.GADI.repository.RatingsRepository;
import com.basic.GADI.repository.ResRepository;
import com.basic.GADI.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ResRepository resRepository;
    private final RatingsRepository ratingsRepository;

    @Transactional
    public List<User> findUserList() {
        return userRepository.findAll();
    }

    @Transactional
    public PageResponseDto<ResDetailResponseDto> findResList(Pageable pageable) {
        Page<Restaurants> restaurantsList =  resRepository.findAllRestaurants(pageable);

        List<Restaurants> restaurantsIds = restaurantsList.getContent();

        List<Long> list = new ArrayList<>();

        for (Restaurants restaurantsId : restaurantsIds) {
            list.add(restaurantsId.getResId());
        }

        List<Restaurants> restaurantsWithDetails = resRepository.findByResDeleteAndResIdIn("N", list);

        List<ResDetailResponseDto> resList = new ArrayList<>();

        for (Restaurants restaurantsWithDetail : restaurantsWithDetails) {
            resList.add(new ResDetailResponseDto(restaurantsWithDetail));
        }

        Page<ResDetailResponseDto> page = new PageImpl<>(resList, pageable, restaurantsList.getTotalElements());

        return new PageResponseDto<>(page);
    }

    @Transactional
    public ResDetailResponseDto findResDetail(Long resId) {
        Restaurants resDetail =  resRepository.findByResDeleteAndResId("N", resId)
                .orElseThrow(()-> new BusinessException("해당하는 음식점을 찾을 수 없습니다."));
        Double average = ratingsRepository.findAverageScoreByResId(resId);
        ResDetailResponseDto resDetailResponseDto = new ResDetailResponseDto(resDetail);
        resDetailResponseDto.setAverage(average);
        return resDetailResponseDto;
    }

    @Transactional
    public void deleteRes(Long resId) {
        Restaurants res = resRepository.findByResId(resId).orElseThrow(() -> new EntityNotFoundException("해당하는 음식점을 찾을 수 없습니다."));
        res.setResDelete("Y");
        resRepository.save(res);
    }
}
