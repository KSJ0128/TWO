package com.togetherwithocean.TWO.Plog.Service;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Plog.DTO.DailyReq;
import com.togetherwithocean.TWO.Plog.DTO.PlogReq;
import com.togetherwithocean.TWO.Plog.Domain.Plog;
import com.togetherwithocean.TWO.Plog.Repository.PlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PlogService {
    private final MemberRepository memberRepository;
    private final PlogRepository plogRepository;

    public Plog savePlog(String email, PlogReq plogReq) {
        Member member = memberRepository.findMemberByEmail(email);


        // member의 상태 갱신 -> MemberService
        // 쓰레기 봉투 수, 총 줍깅 수, 월 줍깅 수, 포인트, 스코어 정보 갱신
        member.setAvailTrashBag(member.getAvailTrashBag() + plogReq.getTrashBag());
        member.setTotalPlog(member.getTotalPlog() + 1);
        member.setMonthlyPlog(member.getMonthlyPlog() + 1);

        // 포인트, 스코어 갱신 함수는 따로 만드는게 좋을듯하다
        member.setPoint(member.getPoint() + plogReq.getTrashBag() * 1000);
        member.setMonthlyScore(member.getMonthlyScore() + plogReq.getTrashBag() * 1000);

        // 추천 지역이면 추가 포인트 지급 로직 추가 필요
//        member.setPoint(member.getPoint() + plogReq.getTrashBag() * 1000);
//        member.setMonthlyScore(member.getMonthlyScore() + plogReq.getTrashBag() * 1000);


        // 사용자의 줍깅 실행 여부 갱신 추가 필요 -> StatisticsService


        Plog plog = Plog.builder()
                .memberNumber(member.getMemberNumber())
                .date(LocalDate.now())
                .trashBag(plogReq.getTrashBag())
                .location(plogReq.getLocation())
                .build();
        plogRepository.save(plog);
        return plog;
    }

    // MemberService
    public Member saveStep(String email, Long step) {
        Member member = memberRepository.findMemberByEmail(email);
        // 사용자의 걸음, 포인트, 스코어 정보 갱신 -> MemberService
        member.setDailyStep(member.getDailyStep() + step);
        member.setPoint(member.getPoint() + step * 1000);
        member.setMonthlyScore(member.getMonthlyScore() + step * 1000);

        // 사용자의 목표 걸음 달성 여부 갱신 필요 -> StatisticsService
//        if (member.getDailyStep() >= step)
//

        return member;
    }

    public List<Plog> getPlogs(String email, DailyReq dailyReq) {
        Member member = memberRepository.findMemberByEmail(email);
        List<Plog> plogs = plogRepository.findPlogByDateAndMemberNumber(dailyReq.getDate(), member.getMemberNumber());
        return plogs;
    }
}
