package com.basic.GADI.dto.response;

import com.basic.GADI.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyInfoResponseDto {

    private String userName;

    private String userEmail;

    private String userFile;

    private String userPhone;

    private String userBirth;

    // User Entity 에서 Dto 로 변환
    public static MyInfoResponseDto fromUser(User user) {
        return MyInfoResponseDto.builder()
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .userFile(user.getUserFile())
                .userPhone(user.getUserPhone())
                .userBirth(user.getUserBirth())
                .build();
    }
}
