package com.togetherwithocean.TWO.Stat.Service;

import com.togetherwithocean.TWO.Badge.Domain.Badge;
import com.togetherwithocean.TWO.Badge.Repository.BadgeRepository;
import com.togetherwithocean.TWO.Badge.Service.BadgeService;
import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import com.togetherwithocean.TWO.MemberBadge.Repository.MemberBadgeRepository;
import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import com.togetherwithocean.TWO.Ranking.Repository.RankingRepository;
import com.togetherwithocean.TWO.Recommend.Domain.Recommend;
import com.togetherwithocean.TWO.Recommend.Repository.RecommendRepository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Stat.DTO.GetMonthlyStatRes;
import com.togetherwithocean.TWO.Stat.DTO.PatchStatWalkReq;
import com.togetherwithocean.TWO.Stat.DTO.PostStatSaveReq;
import com.togetherwithocean.TWO.Stat.DTO.StatRes;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import com.togetherwithocean.TWO.Stat.Repository.StatRepository;
import com.togetherwithocean.TWO.StatLoc.Domain.StatLoc;
import com.togetherwithocean.TWO.StatLoc.Repository.StatLocRepository;
import com.togetherwithocean.TWO.Visit.Domain.Visit;
import com.togetherwithocean.TWO.Visit.Repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {
    @Autowired
    private final MemberRepository memberRepository;
    private final MemberBadgeRepository memberBadgeRepository;
    private final BadgeService badgeService;
    private final StatRepository statRepository;
    private final RankingRepository rankingRepository;
    private final RecommendRepository recommendRepository;
    private final VisitRepository visitRepository;
    private final StatLocRepository statLocRepository;

    public StatRes savePlog(String email, PostStatSaveReq postStatSaveReq) {
        Member member = memberRepository.findMemberByEmail(email);
        Stat stat = statRepository.findStatByMemberAndDate(member, postStatSaveReq.getDate());
        Ranking ranking = member.getRanking();

        // member의 상태 갱신 -> MemberService
        // 신청 가능 쓰레기 봉투 수, 일 쓰레기봉투 수, 일 줍깅 수, 총 줍깅 수 갱신,
        member.setAvailTrashBag(member.getAvailTrashBag() + postStatSaveReq.getTrashBag());
        stat.setTrashBag(stat.getTrashBag() + postStatSaveReq.getTrashBag());
        stat.setPlogging(stat.getPlogging() + 1);
        member.setTotalPlog(member.getTotalPlog() + 1);

        // 포인트 및 스코어 갱신
        member.setPoint(member.getPoint() + postStatSaveReq.getTrashBag() * 100);
        ranking.setScore(ranking.getScore() + postStatSaveReq.getTrashBag() * 10000);

        // 멤버 방문 장소 정보 생성
        Visit visit = Visit.builder()
                .member(member)
                .date(postStatSaveReq.getDate())
                .name(postStatSaveReq.getLocation())
                .recommend(false)
                .latitude(postStatSaveReq.getLatitude())
                .longtitude(postStatSaveReq.getLongtitude())
                .build();

        // 추천 지역이면 추가 포인트 및 스코어 지급
        // 추천 지역이면 visit true로 설정
        Recommend recommend = recommendRepository.findRecommendByName(postStatSaveReq.getLocation());
        if (recommend != null) {
            member.setPoint(member.getPoint() + postStatSaveReq.getTrashBag() * 500);
            ranking.setScore(ranking.getScore() + postStatSaveReq.getTrashBag() * 50000);
            visit.setRecommend(true);
        }

        visitRepository.save(visit);
        statRepository.save(stat);
        memberRepository.save(member);
        rankingRepository.save(ranking);

        // N회 줍깅 달성 여부 확인
        badgeService.doPlogN(member, member.getTotalPlog());

        // 점수 달성 여부 확인
        badgeService.achieveScore(member, ranking.getScore());

        // 스탯-장소 정보 생성
        StatLoc statLoc = StatLoc.builder()
                .stat(stat)
                .visit(visit)
                .build();

        statLocRepository.save(statLoc);

        StatRes statRes = StatRes.builder()
                .statNumber(stat.getStatNumber())
                .date(stat.getDate())
                .attend(stat.getAttend())
                .step(stat.getStep())
                .achieveStep(stat.getAchieveStep())
                .plogging(stat.getPlogging())
                .trashBag(stat.getTrashBag())
                .visit(visitRepository.findVisitNamesByMemberAndDate(member, postStatSaveReq.getDate()))
                .build();

        return statRes;
    }

    // 걷깅이던 줍깅이던 관계 없이 걸음 수 및 포인트 갱신
    public StatRes saveStep(PatchStatWalkReq patchStatWalkReq, String email) {
        Member member = memberRepository.findMemberByEmail(email);
        Stat stat = statRepository.findStatByMemberAndDate(member, patchStatWalkReq.getDate());
        Ranking ranking = member.getRanking();

        // 사용자의 걸음 갱신
        stat.setStep(stat.getStep() + patchStatWalkReq.getStep());

        // 사용자 목표 걸음 성취 여부 갱신
        if (stat.getStep() > member.getStepGoal())
            stat.setAchieveStep(true);

        // 포인트, 스코어 정보 갱신
        member.setPoint(member.getPoint() + (long)(patchStatWalkReq.getStep() * 0.01));
        ranking.setScore(ranking.getScore() + patchStatWalkReq.getStep());

        rankingRepository.save(ranking);
        memberRepository.save(member);
        statRepository.save(stat);

        // 점수 달성 여부 확인
        badgeService.achieveScore(member, ranking.getScore());

        StatRes statRes = StatRes.builder()
                .statNumber(stat.getStatNumber())
                .date(stat.getDate())
                .attend(stat.getAttend())
                .step(stat.getStep())
                .achieveStep(stat.getAchieveStep())
                .plogging(stat.getPlogging())
                .trashBag(stat.getTrashBag())
                .visit(visitRepository.findVisitNamesByMemberAndDate(member, patchStatWalkReq.getDate()))
                .build();

        return statRes;
    }

    public StatRes getDailyPlog(String email, LocalDate date) {
        Member member = memberRepository.findMemberByEmail(email);
        Stat stat = statRepository.findStatByMemberAndDate(member, date);

        StatRes statRes = StatRes.builder()
                .statNumber(stat.getStatNumber())
                .date(stat.getDate())
                .attend(stat.getAttend())
                .step(stat.getStep())
                .achieveStep(stat.getAchieveStep())
                .plogging(stat.getPlogging())
                .trashBag(stat.getTrashBag())
                .visit(visitRepository.findVisitNamesByMemberAndDate(member, date))
                .build();

        return statRes;
    }

    public GetMonthlyStatRes getMonthlyStat(int year, int month, String email) {
        Member member = memberRepository.findMemberByEmail(email);
        Ranking ranking = member.getRanking();

        // Calendar DB에서 특정 유저의 특정 월에 속하는 데이터 리스트 가져옴
        Long monthlyPlogs =  statRepository.getMonthlyPlogging(member, year, month);
        Long monthlyScore =  ranking.getScore();
        List<Stat> monthlyStats = statRepository.getMonthlyStat(member, year, month);

        List<StatRes> monthlyStatRess= new ArrayList<>();

        for (int i = 0; i < monthlyStats.size(); i++) {
            StatRes statRes = StatRes.builder()
                    .statNumber(monthlyStats.get(i).getStatNumber())
                    .date(monthlyStats.get(i).getDate())
                    .attend(monthlyStats.get(i).getAttend())
                    .step(monthlyStats.get(i).getStep())
                    .achieveStep(monthlyStats.get(i).getAchieveStep())
                    .plogging(monthlyStats.get(i).getPlogging())
                    .trashBag(monthlyStats.get(i).getTrashBag())
                    .visit(visitRepository.findVisitNamesByMemberAndDate(member, monthlyStats.get(i).getDate()))
                    .build();
            monthlyStatRess.add(i, statRes);
        }

        return new GetMonthlyStatRes(monthlyPlogs, monthlyScore, monthlyStatRess);
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
