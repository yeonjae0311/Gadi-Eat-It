package com.basic.GADI.dto.response;

import com.basic.GADI.entity.Restaurants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarkerListResponseDto {

    private Long resId;
    private String resName;
    private String resAddress;
    private String resPhone;
    private double latitude;
    private double longitude;
    private double average;

    public MarkerListResponseDto(Restaurants restaurants) {
        this.resId = restaurants.getResId();
        this.resName = restaurants.getResName();
        this.resAddress = restaurants.getResAddress();
        this.resPhone = restaurants.getResPhone();
        this.latitude = restaurants.getResLatitude();
        this.longitude = restaurants.getResLongitude();
    }

}
