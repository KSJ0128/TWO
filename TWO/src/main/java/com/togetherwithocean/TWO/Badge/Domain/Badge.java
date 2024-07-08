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
    @Column(name = "badge_number", nullable = false)
    private Long badgeNumber;

    @Column(name = "badge_name")
    private String badgeName;

    @Column(name = "descript")
    private String descript;

    @Column(name = "mission")
    private String mission;

    @Builder
    public Badge(Long badgeNumber, String badgeName, String descript, String mission) {
        this.badgeNumber = badgeNumber;
        this.badgeName = badgeName;
        this.descript = descript;
        this.mission = mission;
    }
}
