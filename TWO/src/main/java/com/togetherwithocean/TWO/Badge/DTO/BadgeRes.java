package com.togetherwithocean.TWO.Badge.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BadgeRes {
    private Long badgeNumber;
    private String badgeName;
    private String mission;

    @Builder
    BadgeRes(Long badgeNumber, String badgeName, String mission) {
        this.badgeNumber = badgeNumber;
        this.badgeName = badgeName;
        this.mission = mission;
    }
}
