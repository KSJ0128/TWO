package com.togetherwithocean.TWO.Plogging.Service;

import com.togetherwithocean.TWO.PlogCalendar.Domain.PlogCalendar;
import com.togetherwithocean.TWO.PlogCalendar.Repository.PlogCalendarRepository;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Plogging.DTO.PostPlogReq;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class PloggingService {
    private final MemberRepository memberRepository;
    private final PlogCalendarRepository calendarRepository;

    // 매달 1일 각 멤버 월별 줍깅 수 및 스코어 0으로 초기화
    @Scheduled(cron = "0 0 0 1 * ?")
    public void initPloggingTrashNScore() {
        List<Member> allMember = memberRepository.findAll();

        for (int i = 0; i < allMember.size(); i++) {
            allMember.get(i).setMonthlyTrashBag(0L);
            allMember.get(i).setMonthlyScore(0L);
            memberRepository.save(allMember.get(i));
        }
    }

    @Transactional
    public PlogCalendar savePlog(PostPlogReq postPlogReq) {
        PlogCalendar calendar = PlogCalendar.builder()
                .date(postPlogReq.getPlogDate())
                .trashBag(postPlogReq.getPlogTrash())
                .location(postPlogReq.getPlogLoc())
                .step(postPlogReq.getPlogStep())
                .memberNumber(postPlogReq.getMemberNumber())
                .build();
        calendarRepository.save(calendar);
        return calendar;
    }
}
