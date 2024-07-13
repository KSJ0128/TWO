package com.togetherwithocean.TWO.Stat.Repository;

import com.togetherwithocean.TWO.Stat.Domain.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface StatRepository extends JpaRepository<Stat, Long> {
    Stat findStatByMemberNumberAndDate(Long memberNumber, LocalDate date);
    List<Stat> findByMemberNumberAndDateBetween(Long memberNumber, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT SUM(s.plogging) FROM stat s WHERE s.member_number = :member_number AND YEAR(s.date) = :year AND MONTH(s.date) = :month", nativeQuery = true)
    Long getMonthlyPlogging(
            @Param("member_number") Long member_number,
            @Param("year") int year,
            @Param("month") int month
    );
}
