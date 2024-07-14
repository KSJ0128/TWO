package com.togetherwithocean.TWO.Item.DTO;

import com.togetherwithocean.TWO.Item.Domain.Item;
import com.togetherwithocean.TWO.Member.Domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BuyResDTO {
    private Long beforeBuyPoint;
    private Long afterBuyPoint;
    private boolean buy;
    private String itemName;
    private Long itemPrice;

    @Builder
    public BuyResDTO(Long beforeBuyPoint, Long afterBuyPoint, boolean buy, String itemName, Long itemPrice) {
        this.beforeBuyPoint = beforeBuyPoint;
        this.afterBuyPoint = afterBuyPoint;
        this.buy = buy;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
