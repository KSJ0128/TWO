package com.togetherwithocean.TWO.Badge.Service;

import com.togetherwithocean.TWO.Badge.Domain.Badge;
import com.togetherwithocean.TWO.Badge.Repository.BadgeRepository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import com.togetherwithocean.TWO.MemberBadge.Repository.MemberBadgeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    public void successJoin(Member member) {
        System.out.println("뱃지 지급");
        // 바키타 돌고래 배지 지급
        Badge badge = badgeRepository.findBadgeByBadgeNumber(1L);

        MemberBadge memberBadge = MemberBadge.builder()
                .member(member)
                .badge(badge)
                .build();
        System.out.println(memberBadge.toString());
        memberBadgeRepository.save(memberBadge);
    }

    public void buyFirstItem(Member member) {
        // 북대서양 긴수염고래 배지 지급
        Badge badge = badgeRepository.findBadgeByBadgeNumber(2L);
        if (memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge) == null) {
            MemberBadge memberBadge = MemberBadge.builder()
                    .member(member)
                    .badge(badge)
                    .build();
            memberBadgeRepository.save(memberBadge);
        }
    }

    public void buyFirstDeco(Member member) {
        Badge badge = badgeRepository.findBadgeByBadgeNumber(3L);
        if (memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge) == null) {
            MemberBadge memberBadge = MemberBadge.builder()
                    .member(member)
                    .badge(badge)
                    .build();
            memberBadgeRepository.save(memberBadge);
        }
    }


}
