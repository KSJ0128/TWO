package com.togetherwithocean.TWO.MemberItem.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.togetherwithocean.TWO.Item.Domain.Item;
import com.togetherwithocean.TWO.Member.Domain.Member;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberItemDTO {
    private String memberNick;
    private String itemName;
    private boolean equip;
    private Double posX;
    private Double posY;

    @Builder
    public MemberItemDTO(String memberNick, String itemName, boolean equip, Double posX, Double posY) {
        this.memberNick = memberNick;
        this.itemName = itemName;
        this.equip = equip;
        this.posX = posX;
        this.posY = posY;
    }
}
