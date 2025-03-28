package com.basic.GADI.service;

import com.basic.GADI.dto.request.RatingUpdateRequestDto;
import com.basic.GADI.dto.response.MarkerListResponseDto;
import com.basic.GADI.dto.response.RatingResponseDto;
import com.basic.GADI.entity.Ratings;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.entity.User;
import com.basic.GADI.repository.RatingsRepository;
import com.basic.GADI.repository.ResRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final ResRepository resRepository;
    private final RatingsRepository ratingsRepository;

    @Transactional
    public List<MarkerListResponseDto> selectMarkerList() {
        List<Restaurants> res = resRepository.findAll();
        List<MarkerListResponseDto> list = new ArrayList<>();

        for(Restaurants r : res) {
            list.add(new MarkerListResponseDto(r));
        }

        return list;
    }

    @Transactional
    public Ratings updateRating(RatingUpdateRequestDto ratingUpdateRequestDto) {
        User user = new User();
        user.setUserId(ratingUpdateRequestDto.getUserId());

        Restaurants restaurants = new Restaurants();
        restaurants.setResId(ratingUpdateRequestDto.getResId());

        Ratings ratings = ratingsRepository.findByUserAndRestaurants(user, restaurants);

        if(ratings == null) {
            ratings = new Ratings();
            ratings.setUser(user);
            ratings.setRestaurants(restaurants);
            ratings.setScore(ratingUpdateRequestDto.getScore());
        } else {
            ratings.setUser(user);
            ratings.setRestaurants(restaurants);
            ratings.setScore(ratingUpdateRequestDto.getScore());
        }

        return ratingsRepository.save(ratings);
    }

    @Transactional
    public RatingResponseDto getRating(Long resId) {
       double average = ratingsRepository.findAverageScoreByResId(resId);
       RatingResponseDto ratingResponseDto = new RatingResponseDto();
       ratingResponseDto.setAverage(average);
       return ratingResponseDto;
    }

}
