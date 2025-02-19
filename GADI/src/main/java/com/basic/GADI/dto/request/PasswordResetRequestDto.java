package com.basic.GADI.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequestDto {

    private String userEmail;

    @Pattern(regexp = "^[A-Za-z0-9]{6,12}$", message = "비밀번호는 숫자, 영문자 포함의 6~12자리입니다.")
    private String userPw;

    private String token;
}
