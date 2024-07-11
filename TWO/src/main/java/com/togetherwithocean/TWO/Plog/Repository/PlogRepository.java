package com.togetherwithocean.TWO.Plog.Repository;

import com.togetherwithocean.TWO.Plog.Domain.Plog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PlogRepository extends JpaRepository<Plog, Long> {
    List<Plog> findPlogByDateAndMemberNumber(LocalDate date, Long memberNumber);
}
