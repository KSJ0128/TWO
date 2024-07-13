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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class StatService {
    private final MemberRepository memberRepository;
    private final StatRepository statRepository;
    private final LocationRepository locationRepository;

    public Stat savePlog(String email, PostStatSaveReq postStatSaveReq) {
        Member member = memberRepository.findMemberByEmail(email);
        Long memberNumber = member.getMemberNumber();
        Stat stat = statRepository.findStatByMemberNumberAndDate(memberNumber, postStatSaveReq.getDate());

        // member의 상태 갱신 -> MemberService
        // 신청 가능 쓰레기 봉투 수, 일 쓰레기봉투 수, 일 줍깅 수, 총 줍깅 수 갱신
        member.setAvailTrashBag(member.getAvailTrashBag() + postStatSaveReq.getTrashBag());
        stat.setTrashBag(stat.getTrashBag() + postStatSaveReq.getTrashBag());
        stat.setPlogging(stat.getPlogging() + 1);
        member.setTotalPlog(member.getTotalPlog() + 1);

        // 포인트 및 스코어 갱신
        // 포인트 갱신 공식 의논할 필요 있음
        // DB에 스코어 점수가 따로 없음 -> 속성 추가할지 아니면 해당 월, 주마다 데이터 긁어와서 계산할지 !
        member.setPoint(member.getPoint() + postStatSaveReq.getTrashBag() * 10000);

        // 추천 지역이면 추가 포인트 지급 로직 추가 필요
        Location location = locationRepository.findLocationByName(postStatSaveReq.getLocation());
        if (location != null)
            // 추가 포인트
//        member.setPoint(member.getPoint() + plogReq.getTrashBag() * 1000);
//        member.setMonthlyScore(member.getMonthlyScore() + plogReq.getTrashBag() * 1000);

        statRepository.save(stat);
        return stat;
    }

    // 걷깅이던 줍깅이던 관계 없이 걸음 수 및 포인트 갱신
    public Stat saveStep(PatchStatWalkReq patchStatWalkReq, String email) {
        Member member = memberRepository.findMemberByEmail(email);
        Long memberNumber = member.getMemberNumber();
        Stat stat = statRepository.findStatByMemberNumberAndDate(memberNumber, patchStatWalkReq.getDate());

        // 사용자의 걸음 갱신
        stat.setStep(stat.getStep() + patchStatWalkReq.getStep());

        // 포인트, 스코어 정보 갱신
        // 포인트 갱신 공식 의논할 필요 있음
        member.setPoint(member.getPoint() + patchStatWalkReq.getStep() * 1000);
        // DB에 스코어 점수가 따로 없음 -> 속성 추가할지 아니면 해당 월, 주마다 데이터 긁어와서 계산할지 !

        memberRepository.save(member);
        statRepository.save(stat);
        return stat;
    }

    public Stat getPlogs(String email, LocalDate date) {
        Long memberNumber = memberRepository.findMemberByEmail(email).getMemberNumber();
        Stat stat = statRepository.findStatByMemberNumberAndDate(memberNumber, date);

        return stat;
    }

    public GetMonthlyStatRes getMonthlyStat(GetMonthlyCalendarReq getMonthlyCalendarReq, String email) {
        Long memberNumber = memberRepository.findMemberByEmail(email).getMemberNumber();
        LocalDate startDate = getMonthlyCalendarReq.getStartDate();
        LocalDate endDate = getMonthlyCalendarReq.getEndDate();

        // Calendar DB에서 특정 유저의 특정 월에 속하는 데이터 리스트 가져옴
        List<Stat> month = statRepository.findByMemberNumberAndDateBetween(memberNumber, startDate, endDate);
        Long sumPlog = 0L;
        Long sumPoint = 0L;

        for (int i = 0; i < month.size(); i++) {
            sumPlog += month.get(i).getPlogging();
            sumPoint += month.get(i).getStep() * 1000 +  month.get(i).getTrashBag() * 10000;
        }

        return new GetMonthlyStatRes(sumPlog, sumPoint, month);
    }

    public void makeNewStat(Member member, LocalDate date) {
        Stat stat = Stat.builder()
                .member(member)
                .date(date)
                .build();
        statRepository.save(stat);
    }

    void setYesterdayAchieve(Member member, LocalDate date) {
        Stat beforeStat = statRepository.findStatByMemberNumberAndDate(member.getMemberNumber(), date);
        if (beforeStat.getStep() > member.getStepGoal())
            beforeStat.setAchieveStep(true);
        statRepository.save(beforeStat);
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void initDailyAchieve(LocalDate today) {
        List<Member> memberList = memberRepository.findAll();
        LocalDate yesterday = today.minusDays(1);

        for (Member member : memberList) {
            makeNewStat(member, today);
            setYesterdayAchieve(member, yesterday);
        }
    }
}
