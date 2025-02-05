package com.basic.GADI.controller;

import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.repository.ResRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    ResRepository resRepository;

    @GetMapping("/favorites")
    public ResponseEntity<List<Restaurants>> favoriteRestaurantsList(@RequestParam Long userId) {
        List<Restaurants> favoriteRestaurants = resRepository.findFavoriteRestaurantsByUserId(userId);
        return ResponseEntity.ok(favoriteRestaurants);
    }
}
