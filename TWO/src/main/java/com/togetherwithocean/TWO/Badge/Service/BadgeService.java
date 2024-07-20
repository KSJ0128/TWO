package com.togetherwithocean.TWO.Badge.Service;

import com.togetherwithocean.TWO.Badge.DTO.BadgeRes;
import com.togetherwithocean.TWO.Badge.Domain.Badge;
import com.togetherwithocean.TWO.Badge.Repository.BadgeRepository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import com.togetherwithocean.TWO.MemberBadge.Repository.MemberBadgeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final MemberBadgeRepository memberBadgeRepository;


    public List<BadgeRes> findAllBagdes(Member member) {
        List<MemberBadge> memberBadgeList = memberBadgeRepository.findMemberBadgesByMember(member);

        List<BadgeRes> badgeResList = new ArrayList<>();
        for (MemberBadge memberBadge : memberBadgeList){
            Badge badge = memberBadge.getBadge();
            BadgeRes badgeRes = BadgeRes.builder()
                    .badgeNumber(badge.getBadgeNumber())
                    .badgeName(badge.getBadgeName())
                    .mission(badge.getMission())
                    .build();
            badgeResList.add(badgeRes);
        }

        return badgeResList;
    }


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

    public void doPlogN(Member member, Long plogCnt) {
        // 태평양몽크바다표범(1), 매부리 바다거북(3), 켐프각시바다거북(5), 만타가오리(10)
        Long badgeNum = null;
        if (plogCnt == 1)
            badgeNum = 4L; // 태평양몽크바다표범
        else if (plogCnt == 3)
            badgeNum = 5L; // 매부리 바다거북
        else if (plogCnt == 5)
            badgeNum = 6L; // 켐프각시바다거북
        else if (plogCnt == 10)
            badgeNum = 7L; // 만타가오리
        else
            return;

        Badge badge = badgeRepository.findBadgeByBadgeNumber(badgeNum);
        MemberBadge memberBadge = MemberBadge.builder()
                .member(member)
                .badge(badge)
                .build();
        memberBadgeRepository.save(memberBadge);
    }

    public void achieveScore(Member member, Long userScore) {
        // key : 점수, value : 배지번호
        Map<Long, Long> scoreBadgeNum = Map.of(
                100000L, 8L,  // 고래상어(100,000)
                500000L, 9L       // 남방큰돌고래(500,000)
        );

        for (Long score : scoreBadgeNum.keySet()) {
            if (userScore >= score) { // 해당 점수보다 높으면 뱃지 조회
                Long badgeNum = scoreBadgeNum.get(score);
                Badge badge = badgeRepository.findBadgeByBadgeNumber(badgeNum);
                if (memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge) == null) { // 가지고 있지 않은 뱃지
                    MemberBadge memberBadge = MemberBadge.builder()
                            .member(member)
                            .badge(badge)
                            .build();
                    memberBadgeRepository.save(memberBadge);
                }
            }
        }
    }
}
