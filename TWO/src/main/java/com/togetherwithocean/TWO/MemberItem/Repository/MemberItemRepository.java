package com.togetherwithocean.TWO.MemberItem.Repository;

import com.togetherwithocean.TWO.Item.Domain.Item;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.MemberItem.Domain.MemberItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {
    List<MemberItem> findMemberItemsByMemberAndItem(Member member, Item item);
    List<MemberItem> findMemberItemsByMember(Member member);

}
