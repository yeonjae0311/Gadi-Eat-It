package com.basic.GADI.entity;

import com.basic.GADI.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    private String userFile;

    private String userPhone;

    private String userBirth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    public void prePersist() {
        if (this.role == null) {
            this.role = Role.USER;  // 기본값 설정
        }
    }

    @OneToMany(mappedBy = "user")
    private List<Favorites> favorites;


    public void updateMyInfo(String userName,
                             String userPhone, String userBirth) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userBirth = userBirth;
    }

    public void resetUserPw(String encodedPw) {
        this.userPw = encodedPw;
    }
}
