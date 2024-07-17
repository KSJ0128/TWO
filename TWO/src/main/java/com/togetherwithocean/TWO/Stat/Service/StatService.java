package com.togetherwithocean.TWO.Stat.Service;

import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import com.togetherwithocean.TWO.Ranking.Repository.RankingRepository;
import com.togetherwithocean.TWO.Recommend.Domain.Recommend;
import com.togetherwithocean.TWO.Stat.RecommendRepository;
import com.togetherwithocean.TWO.Visit.Repository.LocationRepository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Stat.DTO.GetMonthlyStatRes;
import com.togetherwithocean.TWO.Stat.DTO.PatchStatWalkReq;
import com.togetherwithocean.TWO.Stat.DTO.PostStatSaveReq;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import com.togetherwithocean.TWO.Stat.Repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {
    @Autowired
    private final MemberRepository memberRepository;
    private final StatRepository statRepository;
    private final RankingRepository rankingRepository;
    private final RecommendRepository recommendRepository;

    public Stat savePlog(String email, PostStatSaveReq postStatSaveReq) {
        Member member = memberRepository.findMemberByEmail(email);
        Stat stat = statRepository.findStatByMemberAndDate(member, postStatSaveReq.getDate());
        Long rankingNumber = memberRepository.findRankingNumberByEmail(member.getEmail());
        Ranking ranking = rankingRepository.findRankingByRankingNumber(rankingNumber);

        // member의 상태 갱신 -> MemberService
        // 신청 가능 쓰레기 봉투 수, 일 쓰레기봉투 수, 일 줍깅 수, 총 줍깅 수 갱신
        member.setAvailTrashBag(member.getAvailTrashBag() + postStatSaveReq.getTrashBag());
        stat.setTrashBag(stat.getTrashBag() + postStatSaveReq.getTrashBag());
        stat.setPlogging(stat.getPlogging() + 1);
        member.setTotalPlog(member.getTotalPlog() + 1);

        // 포인트 및 스코어 갱신
        member.setPoint(member.getPoint() + postStatSaveReq.getTrashBag() * 100);
        ranking.setScore(ranking.getScore() + postStatSaveReq.getTrashBag() * 10000);


        // 추천 지역이면 추가 포인트 및 스코어
        Recommend recommend = recommendRepository.findRecommendByName(postStatSaveReq.getLocation());
        if (recommend != null) {
            member.setPoint(member.getPoint() + postStatSaveReq.getTrashBag() * 500);
            ranking.setScore(ranking.getScore() + postStatSaveReq.getTrashBag() * 50000);
        }

        memberRepository.save(member);
        statRepository.save(stat);
        return stat;
    }

    // 걷깅이던 줍깅이던 관계 없이 걸음 수 및 포인트 갱신
    public Stat saveStep(PatchStatWalkReq patchStatWalkReq, String email) {
        Member member = memberRepository.findMemberByEmail(email);
        Stat stat = statRepository.findStatByMemberAndDate(member, patchStatWalkReq.getDate());
        Long rankingNumber = memberRepository.findRankingNumberByEmail(member.getEmail());
        Ranking ranking = rankingRepository.findRankingByRankingNumber(rankingNumber);

        // 사용자의 걸음 갱신
        stat.setStep(stat.getStep() + patchStatWalkReq.getStep());

        // 사용자 목표 걸음 성취 여부 갱신
        if (stat.getStep() > member.getStepGoal())
            stat.setAchieveStep(true);

        // 포인트, 스코어 정보 갱신
        member.setPoint(member.getPoint() + (long)(patchStatWalkReq.getStep() * 0.01));
        ranking.setScore(ranking.getScore() + patchStatWalkReq.getStep());

        memberRepository.save(member);
        statRepository.save(stat);
        return stat;
    }

    public Stat getPlogs(String email, LocalDate date) {
        Member member = memberRepository.findMemberByEmail(email);
        Stat stat = statRepository.findStatByMemberAndDate(member, date);

        return stat;
    }

    public GetMonthlyStatRes getMonthlyStat(int year, int month, String email) {
        Member member = memberRepository.findMemberByEmail(email);
        Long rankingNumber = memberRepository.findRankingNumberByEmail(member.getEmail());
        Ranking ranking = rankingRepository.findRankingByRankingNumber(rankingNumber);

        // Calendar DB에서 특정 유저의 특정 월에 속하는 데이터 리스트 가져옴
        Long monthlyPlogs =  statRepository.getMonthlyPlogging(member, year, month);
        Long monthlyScore =  ranking.getScore();
        List<Stat> monthlyStats = statRepository.getMonthlyStat(member, year, month);

        return new GetMonthlyStatRes(monthlyPlogs, monthlyScore * 10000, monthlyStats);
    }

    public Stat makeNewStat(Member member, LocalDate date) {
        Stat stat = Stat.builder()
                .member(member)
                .date(date)
                .build();
        return statRepository.save(stat);
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void initDailyAchieve() {
        List<Member> memberList = memberRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Member member : memberList) {
            makeNewStat(member, today);
        }
    }

    // 출석 처리 함수 필요
}
