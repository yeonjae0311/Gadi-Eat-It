package com.basic.GADI.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDto {

    private String AccessToken;
    private String RefreshToken;

    @Builder
    public TokenResponseDto(String AccessToken, String RefreshToken) {
        this.AccessToken = AccessToken;
        this.RefreshToken = RefreshToken;
    }

}
