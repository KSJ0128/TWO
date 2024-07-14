package com.togetherwithocean.TWO.Item.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import com.togetherwithocean.TWO.MemberItem.Domain.MemberItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "item")
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_number")
    private Long ItemNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "name_kr")
    private String nameKr;

    @Column(name = "price")
    private Long price;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "item")
    @JsonManagedReference
    private List<MemberItem> membersList = new ArrayList<>();

    @Builder
    public Item(String name, String nameKr, Long price, String category) {
        this.name = name;
        this.nameKr = nameKr;
        this.price = price;
        this.category = category;
    }
}