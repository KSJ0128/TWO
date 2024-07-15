package com.togetherwithocean.TWO.Badge.Domain;

import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
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

    @OneToMany(mappedBy = "badge")
    private List<MemberBadge> membersList = new ArrayList<>();

    @Builder
    public Badge(String badgeName, String descript, String mission) {
        this.badgeName = badgeName;
        this.descript = descript;
        this.mission = mission;
    }
}
