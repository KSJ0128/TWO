package com.togetherwithocean.TWO.Visit.Repository;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Visit.Domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Visit findLocationByName(String name);
    @Query("SELECT v.name FROM Visit v WHERE v.member = :member AND v.date = :date")
    List<String> findVisitNamesByMemberAndDate(@Param("member") Member member, @Param("date") LocalDate date);
}
