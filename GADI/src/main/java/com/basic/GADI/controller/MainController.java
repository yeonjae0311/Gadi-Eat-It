package com.basic.GADI.controller;

import com.basic.GADI.dto.response.MarkerListResponseDto;
import com.basic.GADI.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main")
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("/list")
    public ResponseEntity<List<MarkerListResponseDto>> markerList () {
        return ResponseEntity.ok(mainService.selectMarkerList());
    }
}
