package com.togetherwithocean.TWO.Item.Controller;

import com.togetherwithocean.TWO.Item.DTO.BuyResDTO;
import com.togetherwithocean.TWO.Item.DTO.ItemDTO;
import com.togetherwithocean.TWO.Item.Domain.Item;
import com.togetherwithocean.TWO.Item.Service.ItemSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemSerivce itemSerivce;

    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody ItemDTO itemDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(itemSerivce.saveItem(itemDTO));
    }

    @GetMapping("/buy")
    public ResponseEntity<BuyResDTO> buyItem(@RequestParam String itemName, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.OK).body(itemSerivce.buyItem(itemName, principal.getName()));
    }

}
