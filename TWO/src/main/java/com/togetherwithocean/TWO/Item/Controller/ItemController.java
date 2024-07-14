package com.togetherwithocean.TWO.Item.Controller;

import com.togetherwithocean.TWO.Item.DTO.BuyResDTO;
import com.togetherwithocean.TWO.Item.DTO.EquipDTO;
import com.togetherwithocean.TWO.Item.DTO.ItemDTO;
import com.togetherwithocean.TWO.Item.Domain.Item;
import com.togetherwithocean.TWO.Item.Service.ItemSerivce;
import com.togetherwithocean.TWO.MemberItem.DTO.MemberItemDTO;
import com.togetherwithocean.TWO.MemberItem.Domain.MemberItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<MemberItemDTO>> getItemList(Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.OK).body(itemSerivce.getItemList(principal.getName()));
    }


//    @PostMapping("/equip")
//    public ResponseEntity<MemberItem> equipItem(@RequestBody EquipDTO equipDTO, Authentication principal) {
//        if (principal == null)
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        return ResponseEntity.status(HttpStatus.OK).body(itemSerivce.equipItem(equipDTO, principal.getName()));
//            return ResponseEntity.status(HttpStatus.OK).body("장착 성공");
//        return ResponseEntity.status(HttpStatus.OK).body("장착 실패");
    }
}
