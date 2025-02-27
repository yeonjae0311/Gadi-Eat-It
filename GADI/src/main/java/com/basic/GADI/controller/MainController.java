package com.basic.GADI.controller;

import com.basic.GADI.dto.request.RatingUpdateRequestDto;
import com.basic.GADI.dto.response.MarkerListResponseDto;
import com.basic.GADI.entity.Ratings;
import com.basic.GADI.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/updateRating")
    public ResponseEntity<Ratings> updateRating(@RequestBody RatingUpdateRequestDto ratingUpdateRequestDto) {

        return ResponseEntity.ok(mainService.updateRating(ratingUpdateRequestDto));
    }

}
