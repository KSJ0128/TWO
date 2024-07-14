package com.togetherwithocean.TWO.Item.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DecoDTO {
    private Long memberItemNumber;
    private boolean equip;
    private Double posX;
    private Double posY;

    @Builder
    public DecoDTO(Long memberItemNumber, boolean equip, Double posX, double posY) {
        this.memberItemNumber = memberItemNumber;
        this.equip = equip;
        this.posX = posX;
        this.posY = posY;
    }

}
