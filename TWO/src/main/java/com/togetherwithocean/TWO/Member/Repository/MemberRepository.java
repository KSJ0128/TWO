package com.togetherwithocean.TWO.Member.Repository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 유저명으로 유저 찾기
    Member findByRealName(String realName);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Member findMemberByEmail(String email);

    @Query("SELECT m.nickname FROM Member m WHERE m.ranking = :ranking")
    String findMemberNicknameByRanking(@Param("ranking") Ranking ranking);
}
