package com.basic.GADI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Getter
@Entity
@NamedEntityGraph(name = "Restaurants.FavoritesAndRatings", attributeNodes = {
        @NamedAttributeNode("favorites"),
        @NamedAttributeNode("ratings")})
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

    @ColumnDefault("'N'")
    private String resDelete;

    @Column(columnDefinition = "double")
    private double resLatitude;

    @Column(columnDefinition = "double")
    private double resLongitude;

    @OneToMany(mappedBy = "restaurants", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ratings> ratings;

    @OneToMany(mappedBy = "restaurants", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Favorites> favorites;

    public void setResDelete(String resDelete) {
        this.resDelete = resDelete;
    }
}
