package com.togetherwithocean.TWO.Ranking.Service;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import com.togetherwithocean.TWO.Ranking.Repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
