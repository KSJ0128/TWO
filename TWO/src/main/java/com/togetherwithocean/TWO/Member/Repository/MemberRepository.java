package com.togetherwithocean.TWO.Member.Repository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 유저명으로 유저 찾기
    Member findByRealName(String realName);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Member findMemberByEmail(String email);
    Long findRankingNumberByEmail(String email);
}
