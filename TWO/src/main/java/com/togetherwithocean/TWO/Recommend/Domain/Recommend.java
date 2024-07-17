package com.togetherwithocean.TWO.Recommend.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "recommend")
@NoArgsConstructor
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_number")
    private Long recNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "direction")
    private String direction;

    @Column(name = "recs")
    private Boolean recs;

    @Column(name = "latitude")
    private Double latitude; // 위도

    @Column(name = "longitude")
    private Double longitude; // 경도

    @Builder
    public Recommend(String name, String direction, Double latitude, Double longitude) {
        this.name = name;
        this.direction = direction;
        this.recs = false;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
