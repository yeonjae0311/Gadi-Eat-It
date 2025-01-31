package com.basic.GADI.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    private String userFile;

    @Column(nullable = false)
    @ColumnDefault("U")
    private String userRole;
}
