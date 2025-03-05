package com.basic.GADI.controller;

import com.basic.GADI.config.jwt.JwtUtil;
import com.basic.GADI.dto.request.MyInfoRequestDto;
import com.basic.GADI.dto.request.MyRestaurantRequestDto;
import com.basic.GADI.dto.request.PasswordResetRequestDto;
import com.basic.GADI.dto.response.MyInfoResponseDto;
import com.basic.GADI.dto.response.PageResponseDto;
import com.basic.GADI.dto.response.ResDetailResponseDto;
import com.basic.GADI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/favorites")
    public ResponseEntity<PageResponseDto<ResDetailResponseDto>> favoriteRestaurantsList(@RequestParam Long userId, Pageable pageable) {
        PageResponseDto<ResDetailResponseDto> favoriteRestaurants = userService.myFavoriteRestaurantsList(userId, pageable);
        return ResponseEntity.ok().body(favoriteRestaurants);
    }

    @GetMapping("/my_info")
    public ResponseEntity<MyInfoResponseDto> showMyInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.findMyInfo(userId));
    }

    @PatchMapping("/my_info/update")
    public ResponseEntity<String> updateMyInfo(HttpServletRequest request,
                                               @RequestPart(value = "myInfos", required = false) @Valid MyInfoRequestDto requestDto,
                                               @RequestPart(value = "file", required = false) MultipartFile file) {
        Long userId = (Long) request.getAttribute("userId");
        userService.updateMyInfo(userId, requestDto, file);
        return ResponseEntity.ok().body("내 정보가 수정되었습니다.");
    }

    @PostMapping("/my_pw/check")
    public ResponseEntity<String> checkMyPw(HttpServletRequest request, @RequestBody @Valid PasswordResetRequestDto passwordResetRequestDto) {
        Long userId = (Long) request.getAttribute("userId");
        userService.checkMyPw(userId, passwordResetRequestDto.getUserPw());
        return ResponseEntity.ok().body("기존 비밀번호와 일치합니다.");
    }

    @PatchMapping("/my_pw/reset")
    public ResponseEntity<String> resetPassword(HttpServletRequest request, @RequestBody @Valid PasswordResetRequestDto passwordResetRequestDto)  {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = (Long) request.getAttribute("userId");

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.badRequest().body("토큰이 유효하지 않거나 만료되었습니다.");
        }
        userService.resetUserPw(userId, passwordResetRequestDto.getUserPw());
        return ResponseEntity.ok().body("비밀번호가 재설정되었습니다.");
    }

    @PostMapping("/my_res/add")
    public ResponseEntity<String> addMyRestaurant(HttpServletRequest request, @RequestBody @Valid MyRestaurantRequestDto myRestaurantRequestDto) {
        Long userId = (Long) request.getAttribute("userId");
        userService.addMyRestaurant(userId, myRestaurantRequestDto.getResId());
        return ResponseEntity.ok().body("해당 식당이 즐겨찾기 목록에 추가되었습니다.");
    }
}
