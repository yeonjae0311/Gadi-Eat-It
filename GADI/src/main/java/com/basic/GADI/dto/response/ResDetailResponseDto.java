package com.basic.GADI.dto.response;

import com.basic.GADI.entity.Ratings;
import com.basic.GADI.entity.Restaurants;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private List<Ratings> ratings;

    @Builder
    public ResDetailResponseDto(Long resId, String resName, String resAddress, String resPhoto,
                                String resPhone, String resSector, List<Ratings> ratings) {
        this.resId = resId;
        this.resName = resName;
        this.resAddress = resAddress;
        this.resPhoto = resPhoto;
        this.resPhone = resPhone;
        this.resSector = resSector;
        this.ratings = ratings;
    }

    public ResDetailResponseDto fromEntity(Restaurants restaurants) {
        return ResDetailResponseDto.builder()
                .resId(restaurants.getResId())
                .resName(restaurants.getResName())
                .resAddress(restaurants.getResAddress())
                .resPhone(restaurants.getResPhone())
                .resPhoto(restaurants.getResPhoto())
                .resSector(restaurants.getResSector())
                .ratings(restaurants.getRatings())
                .build();
    }

}
