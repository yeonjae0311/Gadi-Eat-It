package com.basic.GADI.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
@NoArgsConstructor
public class PasswordLinkRequestDto {
    private String userEmail;
    private String token;
}