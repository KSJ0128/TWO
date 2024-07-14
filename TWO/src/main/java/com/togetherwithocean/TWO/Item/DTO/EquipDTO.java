package com.togetherwithocean.TWO.Item.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipDTO {
    private String itemName;
    private Double posX;
    private Double posY;

    @Builder
    public EquipDTO(String itemName, Double posX, double posY) {
        this.itemName = itemName;
        this.posX = posX;
        this.posY = posY;
    }

}
