package com.basic.GADI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyInfoRequestDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String userName;

    private String userFile;

    @Pattern(regexp = "^010-[0-9]{4}-[0-9]{4}$", message = "전화번호 형식은 010-1234-5678 입니다.")
    private String userPhone;

    @Pattern(regexp = "^(19\\d{2}|20\\d{2})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "생년월일 형식은 YYYY-MM-DD 입니다.")
    private String userBirth;
}
