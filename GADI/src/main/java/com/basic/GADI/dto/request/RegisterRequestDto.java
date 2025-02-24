package com.basic.GADI.dto.request;

import com.basic.GADI.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9](\\.?[a-z0-9_-])*@[a-z0-9-]+(\\.[a-z]{2,})+$", message = "아이디는 이메일 형식입니다.")
    private String userEmail;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,12}$", message = "비밀번호는 숫자, 영문자 포함의 6~12자리입니다.")
    private String userPw;

    @Pattern(regexp = "^010-[0-9]{4}-[0-9]{4}$", message = "전화번호 형식은 010-1234-5678 입니다.")
    private String userPhone;

    @Pattern(regexp = "^(19\\d{2}|20\\d{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "생년월일 형식은 YYYY-MM-DD 입니다.")
    private String userBirth;

    // Dto 에서 Entity 로 변환
    public User toEntity(String encodedPw) {
        return User.builder()
                .userEmail(userEmail)
                .userName(userName)
                .userPw(encodedPw)
                .userPhone(userPhone)
                .userBirth(userBirth)
                .build();
    }
}
