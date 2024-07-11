package com.togetherwithocean.TWO.Statistics.Repository;

import com.togetherwithocean.TWO.Statistics.Domain.Statistics;
import com.togetherwithocean.TWO.Statistics.DTO.GetMonthlyStatisticsRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query("SELECT new com.togetherwithocean.TWO.Statistics.DTO.GetMonthlyStatisticsRes(s.date, s.achiveStep, s.achivePlog) " +
            "from Statistics s " +
            "WHERE s.memberNumber = :num " +
            "AND s.date BETWEEN :monthStart AND :monthEnd")
    List<GetMonthlyStatisticsRes> findMonthlyStatisticsByDate(LocalDate monthStart, LocalDate monthEnd, Long num);

}
