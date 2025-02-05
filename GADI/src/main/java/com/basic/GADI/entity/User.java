package com.basic.GADI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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

    @Column(nullable = false)
    @ColumnDefault("'U'")
    private String userRole;

    @OneToMany(mappedBy = "user")
    private List<Favorites> favorites;


    public void updateMyInfo(String userName, String userFile,
                             String userPhone, String userBirth) {
        this.userName = userName;
        this.userFile = userFile;
        this.userPhone = userPhone;
        this.userBirth = userBirth;
    }

    public void resetUserPw(String encodedPw) {
        this.userPw = encodedPw;
    }
}
