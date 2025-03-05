package com.basic.GADI.controller;

import com.basic.GADI.dto.request.RatingUpdateRequestDto;
import com.basic.GADI.dto.response.MarkerListResponseDto;
import com.basic.GADI.dto.response.MyRestaurantResponseDto;
import com.basic.GADI.dto.response.RatingResponseDto;
import com.basic.GADI.entity.Ratings;
import com.basic.GADI.service.MainService;
import com.basic.GADI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<MarkerListResponseDto>> markerList() {

        return ResponseEntity.ok(mainService.selectMarkerList());
    }

    @PostMapping("/updateRating")
    public ResponseEntity<Ratings> updateRating(@RequestBody RatingUpdateRequestDto ratingUpdateRequestDto) {

        return ResponseEntity.ok(mainService.updateRating(ratingUpdateRequestDto));
    }

    @GetMapping("/getRatings/{resId}")
    public ResponseEntity<RatingResponseDto> getRating(@PathVariable Long resId) {
        return ResponseEntity.ok(mainService.getRating(resId));
    }

    @GetMapping("/my_favorite")
    public ResponseEntity<MyRestaurantResponseDto> getMyRestaurant(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        System.out.println(userId);
        System.out.println(userService.getMyRestaurant(userId));
        return ResponseEntity.ok().body(userService.getMyRestaurant(userId));
    }

}
