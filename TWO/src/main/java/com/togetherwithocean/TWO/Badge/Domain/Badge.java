package com.togetherwithocean.TWO.Badge.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "badge")
@NoArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_number")
    private Long badgeNumber;

    @Column(name = "badge_name")
    private String badgeName;

    @Column(name = "descript")
    private String descript;

    @Column(name = "mission")
    private String mission;

    @Builder
    public Badge(String badgeName, String descript, String mission) {
        this.badgeName = badgeName;
        this.descript = descript;
        this.mission = mission;
    }
}
