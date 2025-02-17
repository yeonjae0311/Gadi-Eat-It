package com.basic.GADI.controller;

import com.basic.GADI.dto.response.PageResponseDto;
import com.basic.GADI.dto.response.ResDetailResponseDto;
import com.basic.GADI.entity.User;
import com.basic.GADI.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/user_list")
    public ResponseEntity<List<User>> userList () {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findUserList());
    }

    /*@GetMapping("/res_list")
    public Page<Restaurants> resList(Pageable pageable) {
        return adminService.findResList(pageable);
    }*/

    @GetMapping("/res_list")
    public PageResponseDto<ResDetailResponseDto> resList(Pageable pageable) {
        return adminService.findResList(pageable);
    }

    @GetMapping("/res_detail")
    public ResponseEntity<ResDetailResponseDto> resDetail(@RequestParam Long resId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findResDetail(resId));
    }

    @PatchMapping("/res_delete")
    public ResponseEntity<String> resDelete(@RequestParam Long resId) {
        adminService.deleteRes(resId);
        return ResponseEntity.status(HttpStatus.OK).body("삭제가 완료되었습니다.");
    }
}
