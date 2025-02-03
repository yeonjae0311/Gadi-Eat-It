package com.basic.GADI.controller;

import com.basic.GADI.dto.response.ResDetailResponseDto;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.entity.User;
import com.basic.GADI.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/user_list")
    public ResponseEntity<List<User>> userList () {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findUserList());
    }

    @GetMapping("/res_list")
    public ResponseEntity<List<Restaurants>> resList() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findResList());
    }

    @GetMapping("/res_detail")
    public ResponseEntity<ResDetailResponseDto> resDetail(@RequestParam Long resId) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findResDetail(resId));
    }
}
