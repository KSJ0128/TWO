package com.togetherwithocean.TWO.Ranking.Service;

import com.togetherwithocean.TWO.Badge.Domain.Badge;
import com.togetherwithocean.TWO.Badge.Repository.BadgeRepository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import com.togetherwithocean.TWO.MemberBadge.Repository.MemberBadgeRepository;
import com.togetherwithocean.TWO.Ranking.DTO.RankingDTO;
import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import com.togetherwithocean.TWO.Ranking.Repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    @Autowired
    private final RankingRepository rankingRepository;
    @Autowired
    private final MemberBadgeRepository memberBadgeRepository;
    @Autowired
    private final BadgeRepository badgeRepository;
    private final MemberRepository memberRepository;

    public Ranking makeNewRanking(Member member) {
        Ranking rank = Ranking.builder()
                .member(member)
                .build();
        return rankingRepository.save(rank);
    }

    public List<RankingDTO> getTopRanking() {
        List<Ranking> topTen = rankingRepository.findTop10ByOrderByScoreDesc();
        List<RankingDTO> topTenRanking = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String nickname = memberRepository.findMemberNicknameByRanking(topTen.get(i));
            topTenRanking.add(i, new RankingDTO((long)i+1, nickname, topTen.get(i).getScore()));
        }
        return topTenRanking;
    }

    public RankingDTO getMyRanking(Member member) {
       List<Ranking> allRanking = rankingRepository.findAllByOrderByScoreDesc();
       Ranking myRanking = member.getRanking();
       RankingDTO myRankingDTO = new RankingDTO();

        for (int i = 0; i < allRanking.size(); i++) {
            if (allRanking.get(i).equals(myRanking)) {
                myRankingDTO.setRank((long)i+1);
                myRankingDTO.setName(member.getNickname());
                myRankingDTO.setScore(myRanking.getScore());
                break;
            }
        }
        return myRankingDTO;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void initMonthlyRanking() {
        List<Ranking> rankingList = rankingRepository.findAllByOrderByScoreDesc();

        for (Ranking ranking : rankingList) {
            if (ranking.equals(rankingList.getFirst())) {
                Member member = ranking.getMember();

                Badge badge = badgeRepository.findBadgeByBadgeNumber(10L);

                if (memberBadgeRepository.findMemberBadgeByMemberAndBadge(member, badge) == null) {
                    // 보리고래 배지 지급
                    MemberBadge memberBadge = MemberBadge.builder()
                            .member(member)
                            .badge(badge)
                            .build();
                    memberBadgeRepository.save(memberBadge);
                }
            }
            ranking.setScore(0L);
            rankingRepository.save(ranking);
        }
    }
}
