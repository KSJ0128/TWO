package com.togetherwithocean.TWO.PlogCalendar.Service;

import com.togetherwithocean.TWO.PlogCalendar.DTO.GetMonthlyReq;
import com.togetherwithocean.TWO.PlogCalendar.DTO.GetMonthlyRes;
import com.togetherwithocean.TWO.PlogCalendar.DTO.MonthlyDto;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.PlogCalendar.Repository.PlogCalendarRepository;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PlogCalendarService {
    private final PlogCalendarRepository plogCalendarRepository;
    private final MemberRepository memberRepository;

    public Long calculateDays(LocalDate startDate, LocalDate endDate) {
        Long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        return days;
    }

    public List<GetMonthlyRes> initMonthlyInfo(LocalDate startDate, Long days) {
        List<GetMonthlyRes> monthlyList = new ArrayList<>();

        for (int i = 0; i < days; i++) {
            GetMonthlyRes monthlyRes = new GetMonthlyRes(startDate.plusDays(i));
            monthlyList.add(monthlyRes);
        }
        return monthlyList;
    }

    public List<GetMonthlyRes> getMonthlyInfo(GetMonthlyReq getMonthlyReq, Authentication principal) {
        // 월 시작 일자, 종료 일자
        LocalDate startDate = getMonthlyReq.getStart();
        LocalDate endDate = getMonthlyReq.getEnd();
        Long memberNumber = memberRepository.findMemberByEmail(principal.getName()).getMemberNumber();
        Long days = calculateDays(startDate, endDate);

        // Calendar DB에서 특정 유저의 특정 월에 속하는 데이터 리스트 가져옴
        List<MonthlyDto> monthlyDtos = plogCalendarRepository.findMonthlyDtoByDate(startDate, endDate, memberNumber);
        System.out.println(monthlyDtos);
        List<GetMonthlyRes> monthlyRess = initMonthlyInfo(startDate, days);
        System.out.println(monthlyRess);

        for (MonthlyDto monthlyDto : monthlyDtos) {
            LocalDate d = monthlyDto.getDate();

            // 어떤 일자의 데이터인지 idx로 받아옴
            int idx = (int) ChronoUnit.DAYS.between(startDate, d); // 인덱스를 정확하게 계산

            Long dailyStep = monthlyRess.get(idx).getDailyStep();
            Member member = memberRepository.findMemberByMemberNumber(memberNumber);

            monthlyRess.get(idx).setDailyStep(dailyStep + monthlyDto.getStep());
            if (monthlyRess.get(idx).getDailyStep() > member.getStepGoal())
                monthlyRess.get(idx).setStep(true);
            if (monthlyDto.getPlogging() > 0)
                monthlyRess.get(idx).setPlogging(true);
        }
        return monthlyRess;
    }
}
