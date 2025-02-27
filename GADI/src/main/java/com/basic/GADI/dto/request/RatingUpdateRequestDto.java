package com.basic.GADI.dto.request;

import lombok.Data;

@Data
public class RatingUpdateRequestDto {
    private Double score;
    private Long userId;
    private Long resId;

}
