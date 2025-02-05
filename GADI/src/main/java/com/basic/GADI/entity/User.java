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

    @Column(unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    private String userFile;

    @Column(nullable = false)
    @ColumnDefault("'U'")
    private String userRole;

    @OneToMany(mappedBy = "user")
    private List<Favorites> favorites;

}
