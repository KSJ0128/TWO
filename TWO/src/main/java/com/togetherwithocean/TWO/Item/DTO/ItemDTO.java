package com.togetherwithocean.TWO.Item.DTO;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDTO {
    private String name;
    private String nameKr;
    private Long price;
    private String category;

    @Builder
    public ItemDTO(String name, String nameKr, Long price, String category) {
        this.name = name;
        this.nameKr = nameKr;
        this.price = price;
        this.category = category;
    }
}
