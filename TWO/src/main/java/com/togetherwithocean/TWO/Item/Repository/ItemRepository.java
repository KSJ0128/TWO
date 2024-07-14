package com.togetherwithocean.TWO.Item.Repository;

import com.togetherwithocean.TWO.Item.Domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemByName(String name);
}
