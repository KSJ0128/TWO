package com.togetherwithocean.TWO.Visit.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MyPlaceDTO {
    String name;
    Double latitude;
    Double longtitude;

    @Builder
    public MyPlaceDTO(String name, Double latitude, Double longtitude) {
        this.name = name;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
}
