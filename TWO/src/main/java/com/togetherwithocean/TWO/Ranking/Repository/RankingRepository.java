package com.togetherwithocean.TWO.Ranking.Repository;


import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Ranking findRankingByRankingNumber(Long rankingNumber);

}
