package com.basic.GADI.dto.response;

import com.basic.GADI.entity.Restaurants;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResDetailResponseDto {

    private Long resId;
    private String resName;
    private String resAddress;
    private String resPhoto;
    private String resPhone;
    private String resSector;
    private Double average;

    @Builder
    public ResDetailResponseDto(Long resId, String resName, String resAddress, String resPhoto,
                                String resPhone, String resSector, Double average) {
        this.resId = resId;
        this.resName = resName;
        this.resAddress = resAddress;
        this.resPhoto = resPhoto;
        this.resPhone = resPhone;
        this.resSector = resSector;
        this.average = average;
    }

    public ResDetailResponseDto(Restaurants restaurants) {
        this.resId = restaurants.getResId();
        this.resName = restaurants.getResName();
        this.resAddress = restaurants.getResAddress();
        this.resPhoto = restaurants.getResPhoto();
        this.resPhone = restaurants.getResPhone();
        this.resSector = restaurants.getResSector();
    }

}
