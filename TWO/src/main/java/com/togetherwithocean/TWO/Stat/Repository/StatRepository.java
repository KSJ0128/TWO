package com.togetherwithocean.TWO.Stat.Repository;

import com.togetherwithocean.TWO.Stat.Domain.Stat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StatRepository extends JpaRepository<Stat, Long> {
    Stat findStatByMemberNumberAndDate(Long memberNumber, LocalDate date);
    List<Stat> findByMemberNumberAndDateBetween(Long memberNumber, LocalDate startDate, LocalDate endDate);

    List<Stat> findByDate(LocalDate date);
}
