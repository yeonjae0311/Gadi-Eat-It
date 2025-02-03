package com.basic.GADI.dto.response;

import lombok.Builder;

@Builder
public class TokenResponseDto {

    private String AccessToken;
    private String RefreshToken;

}
