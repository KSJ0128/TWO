package com.togetherwithocean.TWO.Member.Service;

import com.togetherwithocean.TWO.Item.Service.ItemSerivce;
import com.togetherwithocean.TWO.Jwt.JwtProvider;
import com.togetherwithocean.TWO.Jwt.TokenDto;
import com.togetherwithocean.TWO.Member.Authority;
import com.togetherwithocean.TWO.Member.DTO.MainInfoRes;
import com.togetherwithocean.TWO.Member.DTO.MemberJoinReq;
import com.togetherwithocean.TWO.Member.DTO.PostSignInRes;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import com.togetherwithocean.TWO.Stat.Repository.StatRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final StatRepository statRepository;
    private final ItemSerivce itemSerivce;
    private final JwtProvider jwtProvider;
    private final Logger log = LoggerFactory.getLogger(getClass());


    // 유저명으로 유저 찾기
    @Transactional
    public Member getMemberByRealName(String realName) {
        return memberRepository.findByRealName(realName);
    }

    public boolean isNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public boolean isEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public Member save(MemberJoinReq memberSave) {
        System.out.println(memberSave.getPasswd() + " "+ memberSave.getCheckPasswd());

        // 비밀번호 일치 여부 예외처리?
        if (!memberSave.getPasswd().equals(memberSave.getCheckPasswd()))
            return null; // 비밀번호가 일치하지 않습니다

        System.out.println("비밀번호 일치?");
        Member member = Member.builder()
                .realName(memberSave.getRealName())
                .nickname(memberSave.getNickname())
                .email(memberSave.getEmail())
                .passwd(memberSave.getPasswd())
                .phoneNumber(memberSave.getPhoneNumber())
                .postalCode(memberSave.getPostalCode())
                .address(memberSave.getAddress())
                .detailAddress(memberSave.getDetailAddress())
                .charId(memberSave.getCharId())
                .charName(memberSave.getCharName())
                .stepGoal(memberSave.getStepGoal())
                .build();
        memberRepository.save(member);
        return member;
    }
 
    @Transactional
    public void updatePasswd(Member newMember) { memberRepository.save(newMember); }

    @Transactional
    public TokenDto setTokenInHeader(Member loginMember, HttpServletResponse response) {
        // 토큰 생성
        TokenDto jwtToken = jwtProvider.generateToken(loginMember);

        // 액세스 토큰 발급
        log.info("request email = {}, password = {}", loginMember.getEmail(), loginMember.getPasswd());
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        // Header에 정보 넘겨주기
        response.setHeader("MemberEmail", loginMember.getEmail());
        response.setHeader("TokenType", "Bearer");
        response.setHeader("AccessToken", jwtToken.getAccessToken());
        response.setHeader("RefreshToken", jwtToken.getRefreshToken());

        return jwtToken;
    }

    public PostSignInRes setSignInInfo(Member loginMember, TokenDto token) {
        return new PostSignInRes(loginMember, token);
    }

    public MainInfoRes getMainInfo(String email) {
        Member member = memberRepository.findMemberByEmail(email);

        LocalDate today = LocalDate.now();
        System.out.println("today : " + today.getYear() + " " + today.getMonthValue() + " \n");

        Stat stat = statRepository.findStatByMemberAndDate(member, today);

        Long monthlyPlogging = statRepository.getMonthlyPlogging(member, today.getYear(), today.getMonthValue());

        MainInfoRes mainInfo =MainInfoRes.builder()
                .nickname(member.getNickname())
                .charId(member.getCharId())
                .charName(member.getCharName())
                .stepGoal(member.getStepGoal())
                .dailyStep(stat.getStep())
                .monthlyPlog(monthlyPlogging)
                .equipItemList(itemSerivce.getEquipItemList(email))
                .build();
        return mainInfo;
    }
}
