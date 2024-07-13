package com.togetherwithocean.TWO.Location.Domain;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import com.togetherwithocean.TWO.StatLoc.Domain.StatLoc;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "location")
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_number")
    private Long locationNumber;

    @Column(name = "direction")
    private String direction;

    @OneToMany(mappedBy = "location")
    private List<StatLoc> statList = new ArrayList<>();

    @Builder
    public Location(String direction) {
        this.direction = direction;
    }
}

