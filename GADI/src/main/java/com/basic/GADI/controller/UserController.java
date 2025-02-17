package com.basic.GADI.controller;

import com.basic.GADI.dto.request.MyInfoRequestDto;
import com.basic.GADI.dto.response.MyInfoResponseDto;
import com.basic.GADI.dto.response.PageResponseDto;
import com.basic.GADI.dto.response.ResDetailResponseDto;
import com.basic.GADI.repository.ResRepository;
import com.basic.GADI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final ResRepository resRepository;
    private final UserService userService;

    @GetMapping("/favorites")
    public ResponseEntity<PageResponseDto<ResDetailResponseDto>> favoriteRestaurantsList(@RequestParam Long userId, Pageable pageable) {
        PageResponseDto<ResDetailResponseDto> favoriteRestaurants = userService.myFavoriteRestaurantsList(userId, pageable);
        return ResponseEntity.ok().body(favoriteRestaurants);
    }

    @GetMapping("/my_info")
    public ResponseEntity<MyInfoResponseDto> showMyInfo(HttpServletRequest request) {
        long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.findMyInfo(userId));
    }

    @PostMapping("/my_info/update")
    public ResponseEntity<String> updateMyInfo(@RequestParam String userEmail, @RequestBody @Valid MyInfoRequestDto requestDto) {
        userService.updateMyInfo(userEmail, requestDto);
        return ResponseEntity.ok().body("내 정보가 수정되었습니다.");
    }



}
