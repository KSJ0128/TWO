package com.togetherwithocean.TWO.Statistics.Service;

import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Statistics.DTO.GetMonthlyStatisticsReq;
import com.togetherwithocean.TWO.Statistics.DTO.GetMonthlyStatisticsRes;
import lombok.RequiredArgsConstructor;
import com.togetherwithocean.TWO.Statistics.Repository.StatisticsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final MemberRepository memberRepository;

    public List<GetMonthlyStatisticsRes> getMonthlyInfo(GetMonthlyStatisticsReq getMonthlyStatisticsReq, Authentication principal) {
        // 월 시작 일자, 종료 일자
        LocalDate startDate = getMonthlyStatisticsReq.getStart();
        LocalDate endDate = getMonthlyStatisticsReq.getEnd();
        Long memberNumber = memberRepository.findMemberByEmail(principal.getName()).getMemberNumber();

        // Calendar DB에서 특정 유저의 특정 월에 속하는 데이터 리스트 가져옴
        return statisticsRepository.findMonthlyStatisticsByDate(startDate, endDate, memberNumber);
    }
}
