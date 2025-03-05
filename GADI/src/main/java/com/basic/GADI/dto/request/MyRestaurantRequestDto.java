package com.basic.GADI.dto.request;

import com.basic.GADI.entity.Favorites;
import com.basic.GADI.entity.Restaurants;
import com.basic.GADI.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyRestaurantRequestDto {

    private Long resId;

    public static Favorites toEntity(User user, Restaurants restaurants) {
        return Favorites.builder()
                .user(user)
                .restaurants(restaurants)
                .build();

    }
}
