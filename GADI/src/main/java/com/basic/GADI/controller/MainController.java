package com.basic.GADI.controller;

import com.basic.GADI.dto.response.MarkerListResponseDto;
import com.basic.GADI.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/list")
    public ResponseEntity<List<MarkerListResponseDto>> markerList () {
        return ResponseEntity.ok(mainService.selectMarkerList());
    }

}
