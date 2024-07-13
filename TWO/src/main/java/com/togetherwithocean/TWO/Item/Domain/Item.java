package com.togetherwithocean.TWO.Item.Domain;

import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import com.togetherwithocean.TWO.MemberItem.Domain.MemberItem;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "item")
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_number", nullable = false)
    private Long itemNumber;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_price")
    private Long itemPrice;

    @OneToMany(mappedBy = "item")
    private List<MemberItem> membersList = new ArrayList<>();

    @Builder
    public Item(String itemName, Long itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}