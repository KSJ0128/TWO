package com.togetherwithocean.TWO.Ranking.Repository;


import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Ranking.DTO.RankingDTO;
import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Ranking findRankingByRankingNumber(Long rankingNumber);

    List<Ranking> findTop10ByOrderByScoreDesc();
}
