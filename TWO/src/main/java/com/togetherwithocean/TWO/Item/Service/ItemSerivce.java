package com.togetherwithocean.TWO.Item.Service;

import com.togetherwithocean.TWO.Badge.Domain.Badge;
import com.togetherwithocean.TWO.Badge.Repository.BadgeRepository;
import com.togetherwithocean.TWO.Badge.Service.BadgeService;
import com.togetherwithocean.TWO.Item.DTO.BuyResDTO;
import com.togetherwithocean.TWO.Item.DTO.DecoDTO;
import com.togetherwithocean.TWO.Item.DTO.ItemDTO;
import com.togetherwithocean.TWO.Item.Domain.Item;
import com.togetherwithocean.TWO.Item.Repository.ItemRepository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import com.togetherwithocean.TWO.MemberBadge.Repository.MemberBadgeRepository;
import com.togetherwithocean.TWO.MemberItem.DTO.MemberItemDTO;
import com.togetherwithocean.TWO.MemberItem.Domain.MemberItem;
import com.togetherwithocean.TWO.MemberItem.Repository.MemberItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemSerivce {
    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeRepository badgeRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final MemberItemRepository memberItemRepository;
    private final BadgeService badgeService;

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

        badgeService.buyFirstItem(member);

        BuyResDTO buyResDTO = BuyResDTO.builder()
                        .beforeBuyPoint(beforePoint)
                        .afterBuyPoint(member.getPoint())
                        .buy(buy)
                        .itemName(item.getName())
                        .itemPrice(item.getPrice())
                        .build();

        return buyResDTO;
    }

    public List<MemberItemDTO> getItemList(String email) {
        Member member = memberRepository.findMemberByEmail(email);
        List<MemberItem> memberItemList = memberItemRepository.findMemberItemsByMember(member);

        List<MemberItemDTO> memberItemDTOList = new ArrayList<>();
        for (MemberItem memberItem : memberItemList) {
            MemberItemDTO memberItemDTO = MemberItemDTO.builder()
                    .memberItemNumber(memberItem.getMemberItemNumber())
                    .memberNick(member.getNickname())
                    .itemName(memberItem.getItem().getName())
                    .equip(memberItem.isEquip())
                    .posX(memberItem.getPosX())
                    .posY(memberItem.getPosY())
                    .build();
            memberItemDTOList.add(memberItemDTO);
        }
        return memberItemDTOList;
    }

    public List<MemberItemDTO> getEquipItemList(String email) {
        Member member = memberRepository.findMemberByEmail(email);
        List<MemberItem> memberItemList = memberItemRepository.findMemberItemsByMember(member);

        List<MemberItemDTO> memberItemDTOList = new ArrayList<>();
        for (MemberItem memberItem : memberItemList) {
            if (memberItem.isEquip()) {
                MemberItemDTO memberItemDTO = MemberItemDTO.builder()
                        .memberItemNumber(memberItem.getMemberItemNumber())
                        .memberNick(member.getNickname())
                        .itemName(memberItem.getItem().getName())
                        .equip(memberItem.isEquip())
                        .posX(memberItem.getPosX())
                        .posY(memberItem.getPosY())
                        .build();
                memberItemDTOList.add(memberItemDTO);
            }
        }
        return memberItemDTOList;
    }

    public List<MemberItemDTO> decoItem(DecoDTO decoDTO, String email) {
        MemberItem memberItem = memberItemRepository.findByMemberItemNumber(decoDTO.getMemberItemNumber());
        memberItem.setEquip(decoDTO.isEquip());
        memberItem.setPosX(decoDTO.getPosX());
        memberItem.setPosY(decoDTO.getPosY());
        memberItemRepository.save(memberItem);

        // 상괭이 배지 지급
        Member member = memberRepository.findMemberByEmail(email);
        Badge badge = badgeRepository.findBadgeByBadgeNumber(3L);
        if (memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge) == null) {
            MemberBadge memberBadge = MemberBadge.builder()
                    .member(member)
                    .badge(badge)
                    .build();
            memberBadgeRepository.save(memberBadge);
        }

        return getItemList(email);
    }
}

