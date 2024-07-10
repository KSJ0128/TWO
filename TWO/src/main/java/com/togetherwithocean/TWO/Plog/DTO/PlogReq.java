package com.togetherwithocean.TWO.Plog.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlogReq {
    Long trashBag;
    String location;

    @Builder
    public PlogReq(Long trashBag, String location) {
        this.trashBag = trashBag;
        this.location = location;
    }
}
