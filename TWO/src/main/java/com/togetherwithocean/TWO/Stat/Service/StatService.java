package com.togetherwithocean.TWO.Stat.Service;

import com.togetherwithocean.TWO.Location.Domain.Location;
import com.togetherwithocean.TWO.Location.Repository.LocationRepository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Stat.DTO.GetMonthlyCalendarReq;
import com.togetherwithocean.TWO.Stat.DTO.GetMonthlyStatRes;
import com.togetherwithocean.TWO.Stat.DTO.PatchStatWalkReq;
import com.togetherwithocean.TWO.Stat.DTO.PostStatSaveReq;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import com.togetherwithocean.TWO.Stat.Repository.StatRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {
    private final MemberRepository memberRepository;
    private final StatRepository statRepository;
    private final LocationRepository locationRepository;

    public Stat savePlog(String email, PostStatSaveReq postStatSaveReq) {
        Member member = memberRepository.findMemberByEmail(email);
        Stat stat = statRepository.findStatByMemberAndDate(member, postStatSaveReq.getDate());
        System.out.println(stat);

        // member의 상태 갱신 -> MemberService
        // 신청 가능 쓰레기 봉투 수, 일 쓰레기봉투 수, 일 줍깅 수, 총 줍깅 수 갱신
        member.setAvailTrashBag(member.getAvailTrashBag() + postStatSaveReq.getTrashBag());
        stat.setTrashBag(stat.getTrashBag() + postStatSaveReq.getTrashBag());
        stat.setPlogging(stat.getPlogging() + 1);
        member.setTotalPlog(member.getTotalPlog() + 1);

        // 포인트 및 스코어 갱신
        // 포인트 갱신 공식 의논할 필요 있음
        member.setPoint(member.getPoint() + postStatSaveReq.getTrashBag() * 100);

        // 추천 지역이면 추가 포인트 지급 로직 추가 필요
//        Location location = locationRepository.findLocationByName(postStatSaveReq.getLocation());
//        if (location != null)
            // 추가 포인트

        // DB에 스코어 점수가 따로 없음 -> 속성 추가할지 아니면 해당 월, 주마다 데이터 긁어와서 계산할지 !
//      member.setMonthlyScore(member.getMonthlyScore() + plogReq.getTrashBag() * 1000);
        statRepository.save(stat);
        return stat;
    }

    // 걷깅이던 줍깅이던 관계 없이 걸음 수 및 포인트 갱신
    public Stat saveStep(PatchStatWalkReq patchStatWalkReq, String email) {
        Member member = memberRepository.findMemberByEmail(email);
        Stat stat = statRepository.findStatByMemberAndDate(member, patchStatWalkReq.getDate());

        // 사용자의 걸음 갱신
        stat.setStep(stat.getStep() + patchStatWalkReq.getStep());

        // 사용자 목표 걸음 성취 여부 갱신
        if (stat.getStep() > member.getStepGoal())
            stat.setAchieveStep(true);

        // 포인트, 스코어 정보 갱신
        member.setPoint(member.getPoint() + (long)(patchStatWalkReq.getStep() * 0.01));

        // DB에 스코어 점수가 따로 없음 -> 속성 추가할지 아니면 해당 월, 주마다 데이터 긁어와서 계산할지 !

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

        // Calendar DB에서 특정 유저의 특정 월에 속하는 데이터 리스트 가져옴
        Long monthlyPlogs =  statRepository.getMonthlyPlogging(member, year, month);
        Long monthlyScore =  statRepository.getMonthlyTrashBag(member, year, month);
        List<Stat> monthlyStats = statRepository.getMonthlyStat(member, year, month);

        // Score 계산
        // 추후엔 랭킹 DB 만들어서 거기서 뽑아오면 될 듯
        // 지금은 임의로 로직 작성해두겠음
        return new GetMonthlyStatRes(monthlyPlogs, monthlyScore * 10000, monthlyStats);
    }

    public void makeNewStat(Member member, LocalDate date) {
        Stat stat = Stat.builder()
                .member(member)
                .date(date)
                .build();
        statRepository.save(stat);
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void initDailyAchieve() {
        List<Member> memberList = memberRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Member member : memberList) {
            makeNewStat(member, today);
        }
    }
}
