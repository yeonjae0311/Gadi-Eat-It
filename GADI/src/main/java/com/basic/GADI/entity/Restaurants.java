package com.basic.GADI.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Restaurants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resId;

    @Column(nullable = false)
    private String resName;

    @Column(nullable = false)
    private String resAddress;

    @Column
    private String resSector;

    @Column
    private String resPhone;

    @Column
    private String resPhoto;

    @ColumnDefault("'Y'")
    private String resDelete;
}
