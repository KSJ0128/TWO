package com.togetherwithocean.TWO.MemberItem.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.togetherwithocean.TWO.Item.Domain.Item;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "member_item")
public class MemberItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_item_number")
    private Long MemberItemNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_number")
    @JsonBackReference
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_number")
    @JsonBackReference
    private Item item;

    @Column(name = "equip")
    private boolean equip;

    @Nullable
    @Column(name = "pos_x")
    private Double posX;

    @Nullable
    @Column(name = "pos_y")
    private Double posY;

    @Builder
    public MemberItem(Member member, Item item) {
        this.member = member;
        this.item = item;
        this.equip = false;
        this.posX = null;
        this.posY = null;
    }
}
