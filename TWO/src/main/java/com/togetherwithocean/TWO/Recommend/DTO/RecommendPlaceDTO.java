package com.togetherwithocean.TWO.Recommend.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecommendPlaceDTO {
    String name;
    Double latitude;
    Double longtitude;
    String direction;

    @Builder
    public RecommendPlaceDTO(String name, Double latitude, Double longtitude, String direction) {
        this.name = name;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.direction = direction;
    }
}
