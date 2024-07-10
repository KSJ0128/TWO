package com.togetherwithocean.TWO.PlogCalendar.Repository;

import com.togetherwithocean.TWO.PlogCalendar.DTO.MonthlyDto;
import com.togetherwithocean.TWO.PlogCalendar.Domain.PlogCalendar;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PlogCalendarRepository extends JpaRepository<PlogCalendar, Long> {
    @Query("SELECT new com.togetherwithocean.TWO.PlogCalendar.DTO.MonthlyDto(c.date, c.trashBag, c.step) " +
            "from PlogCalendar c " +
            "WHERE c.memberNumber = :num " +
            "AND c.date BETWEEN :monthStart AND :monthEnd")
    List<MonthlyDto> findMonthlyDtoByDate(LocalDate monthStart, LocalDate monthEnd, Long num);
}
