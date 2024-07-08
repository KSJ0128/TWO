package com.togetherwithocean.TWO.MemberItem.Domain;

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
    @Column(name = "member_number", nullable = false)
    private Long memberNumber;

    @Id
    @Column(name = "item_number", nullable = false)
    private Long itemNumber;

    @Column(name = "equip")
    private Boolean equip;

    @Builder
    public MemberItem(Long memberNumber, Long itemNumber, Boolean equip) {
        this.memberNumber = memberNumber;
        this.itemNumber = itemNumber;
        this.equip = equip;
    }
}
