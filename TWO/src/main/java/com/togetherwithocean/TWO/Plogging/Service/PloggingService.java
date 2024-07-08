package com.togetherwithocean.TWO.Plogging.Service;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PloggingService {
    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void initPloggingTrashNScore() {
        List<Member> allMember = memberRepository.findAll();

        for (int i = 0; i < allMember.size(); i++) {
            allMember.get(i).setMonthlyTrashBag(0L);
            allMember.get(i).setMonthlyScore(0L);
            memberRepository.save(allMember.get(i));
        }
    }
}
