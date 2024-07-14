package com.togetherwithocean.TWO.Item.Service;

import com.togetherwithocean.TWO.Item.DTO.BuyResDTO;
import com.togetherwithocean.TWO.Item.DTO.ItemDTO;
import com.togetherwithocean.TWO.Item.Domain.Item;
import com.togetherwithocean.TWO.Item.Repository.ItemRepository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.MemberItem.Domain.MemberItem;
import com.togetherwithocean.TWO.MemberItem.Repository.MemberItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemSerivce {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final MemberItemRepository memberItemRepository;

    public Item saveItem(ItemDTO itemDTO) {
        Item item = Item.builder()
                .name(itemDTO.getName())
                .nameKr(itemDTO.getNameKr())
                .price(itemDTO.getPrice())
                .category(itemDTO.getCategory())
                .build();
        return itemRepository.save(item);
    }

    public BuyResDTO buyItem(String itemName, String email) {
        Member member = memberRepository.findMemberByEmail(email);
        Item item = itemRepository.findItemByName(itemName);

        Long beforePoint = member.getPoint();
        boolean buy;
        // 사용자가 구매할 수 있는지 확인
        if (member.getPoint() < item.getPrice())
            buy = false;
        else {
            buy = true;
            member.setPoint(member.getPoint() - item.getPrice());
            memberRepository.save(member);

            MemberItem memberItem = MemberItem.builder()
                .member(member)
                .item(item)
                .build();
            memberItemRepository.save(memberItem);
        }
        return BuyResDTO.builder()
                .beforeBuyPoint(beforePoint)
                .afterBuyPoint(member.getPoint())
                .buy(buy)
                .itemName(item.getName())
                .itemPrice(item.getPrice())
                .build();
    }
}
