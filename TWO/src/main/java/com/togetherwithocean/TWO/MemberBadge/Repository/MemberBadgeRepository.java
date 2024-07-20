package com.togetherwithocean.TWO.MemberBadge.Repository;

import com.togetherwithocean.TWO.Badge.Domain.Badge;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {
    MemberBadge findMemberBadgeByMemberAndBadge(Member member, Badge badge);
    List<MemberBadge> findMemberBadgesByMember(Member member);
}
