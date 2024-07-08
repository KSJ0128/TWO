package com.togetherwithocean.TWO.Location.Domain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "location")
@NoArgsConstructor
public class Location {

    @Id
    @Column(name = "loc_number", nullable = false)
    private Long locNumber;

    @Column(name = "direction")
    private String direction;

    @Column(name = "recommend")
    private Date recommend;

    @Builder
    public Location(Long locNumber, String direction, Date recommend) {
        this.locNumber = locNumber;
        this.direction = direction;
        this.recommend = recommend;
    }
}

