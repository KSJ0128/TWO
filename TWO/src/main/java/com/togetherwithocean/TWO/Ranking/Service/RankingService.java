package com.togetherwithocean.TWO.Ranking.Service;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Ranking.DTO.RankingDTO;
import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import com.togetherwithocean.TWO.Ranking.Repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingRepository rankingRepository;
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
}
