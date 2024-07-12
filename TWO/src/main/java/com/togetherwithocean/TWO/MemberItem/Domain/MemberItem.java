package com.togetherwithocean.TWO.MemberItem.Domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "member_item")
@NoArgsConstructor
public class MemberItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_item_number")
    private Long MemberItemNumber;

    @Column(name = "member_number")
    private Long memberNumber;

    @Column(name = "item_number")
    private Long itemNumber;

    @Column(name = "equip")
    private boolean equip;

    @Nullable
    @Column(name = "pos_x")
    private Double posX;

    @Nullable
    @Column(name = "pos_y")
    private Double posY;

    @Builder
    public MemberItem(Long memberNumber, Long itemNumber) {
        this.memberNumber = memberNumber;
        this.itemNumber = itemNumber;
        this.equip = false;
        this.posX = null;
        this.posY = null;
    }
}
