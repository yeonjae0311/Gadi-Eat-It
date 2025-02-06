package com.basic.GADI.controller;

import com.basic.GADI.dto.request.MyInfoRequestDto;
import com.basic.GADI.dto.response.MyInfoResponseDto;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.repository.ResRepository;
import com.basic.GADI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    ResRepository resRepository;

    @Autowired
    UserService userService;

    @GetMapping("/favorites")
    public ResponseEntity<List<Restaurants>> favoriteRestaurantsList(@RequestParam Long userId) {
        List<Restaurants> favoriteRestaurants = resRepository.findEntityGraphByFavoritesUserUserId(userId);
        return ResponseEntity.ok(favoriteRestaurants);
    }

    @GetMapping("/my_info")
    public ResponseEntity<MyInfoResponseDto> showMyInfo(@RequestParam String userEmail) {
        return ResponseEntity.ok(userService.findMyInfo(userEmail));
    }

    @PostMapping("/my_info/update")
    public ResponseEntity<String> updateMyInfo(@RequestParam String userEmail, @RequestBody @Valid MyInfoRequestDto requestDto) {
        userService.updateMyInfo(userEmail, requestDto);
        return ResponseEntity.ok().body("내 정보가 수정되었습니다.");
    }



}
