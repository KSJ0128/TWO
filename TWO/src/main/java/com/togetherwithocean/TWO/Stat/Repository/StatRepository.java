package com.togetherwithocean.TWO.Stat.Repository;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StatRepository extends JpaRepository<Stat, Long> {
    Stat findStatByMemberAndDate(Member member, LocalDate date);

    @Query("Select s FROM Stat s WHERE s.member = :member AND YEAR(s.date) = :year AND MONTH(s.date) = :month")
    List<Stat> getMonthlyStat(Member member, int year, int month);


    @Query("SELECT SUM(s.plogging)" +
            "FROM Stat s WHERE s.member = :member AND YEAR(s.date) = :year AND MONTH(s.date) = :month")
    Long getMonthlyPlogging(Member member, int year, int month);

    // trashBag 리턴은 임의로 스코어 계산하려고 넣어둔 거임
    // 나중에 랭킹 DB 파면 수정 필요
    @Query("SELECT SUM(s.trashBag)" +
            "FROM Stat s WHERE s.member = :member AND YEAR(s.date) = :year AND MONTH(s.date) = :month")
    Long getMonthlyTrashBag(Member member, int year, int month);

//    @Query(value = "SELECT SUM(s.plogging) AS monthlyPlog, " +
//            "SUM(s.trash_bag) AS monthlyScore, " +
//            "s.* AS monthlyCalendar" + // 모든 Stat 컬럼 선택
//            "FROM stat s " +
//            "WHERE s.member = :member " +
//            "AND YEAR(s.date) = :year " +
//            "AND MONTH(s.date) = :month " +
//            "GROUP BY s.member_member_number", // 멤버로 그룹화
//            nativeQuery = true)
//    List<Object[]> getMonthlyStat(Member member, int year, int month);
}
