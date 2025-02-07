package com.basic.GADI.service;

import com.basic.GADI.dto.request.MyInfoRequestDto;
import com.basic.GADI.dto.response.MyInfoResponseDto;
import com.basic.GADI.dto.response.PageResponseDto;
import com.basic.GADI.dto.response.ResDetailResponseDto;
import com.basic.GADI.entity.Favorites;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.entity.User;
import com.basic.GADI.exception.BusinessException;
import com.basic.GADI.repository.ResRepository;
import com.basic.GADI.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResRepository resRepository;

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

   /* public PageResponseDto<ResDetailResponseDto> favoriteRestaurantsList(Long userId, Pageable pageable) {

        Page<Restaurants> myFavoriteResList = resRepository.findByFavoritesUserUserId(userId, pageable);

        List<ResDetailResponseDto> resList = new ArrayList<>();
        for (Restaurants restaurant : myFavoriteResList.getContent()) {
            resList.add(new ResDetailResponseDto(restaurant));
        }
        Page<ResDetailResponseDto> page = new PageImpl<>(resList, pageable, myFavoriteResList.getTotalElements());

        return new PageResponseDto<>(page);
    }*/

    public PageResponseDto<ResDetailResponseDto> myFavoriteRestaurantsList (Long userId, Pageable pageable) {
        Page<Favorites> myFavoriteList = favoriteRepository.findByUserUserId(userId, pageable);
        List<Long> myFavoriteResIds = new ArrayList<>();
        for (Favorites favorite : myFavoriteList) {
            myFavoriteResIds.add(favorite.getRestaurants().getResId());
        }
        Page<Restaurants> myRestaurants = resRepository.findByResIdIn(myFavoriteResIds, pageable);

        List<ResDetailResponseDto>  myRestaurantList = new ArrayList<>();
        for (Restaurants restaurant : myRestaurants.getContent()) {
            myRestaurantList.add(new ResDetailResponseDto(restaurant));
        }

        Page<ResDetailResponseDto> page = new PageImpl<>(myRestaurantList, pageable, myRestaurants.getTotalElements());

        return new PageResponseDto<>(page);


    }
}
