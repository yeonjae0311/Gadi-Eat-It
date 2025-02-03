package com.basic.GADI.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Builder
    public RefreshToken(String token, User user) {
        this.refreshToken = token;
        this.user = user;
    }

    public void updateToken(String token) {
        this.refreshToken = token;
    }

}
