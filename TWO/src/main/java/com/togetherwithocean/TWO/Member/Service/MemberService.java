package com.togetherwithocean.TWO.Member.Service;

import com.togetherwithocean.TWO.Jwt.JwtProvider;
import com.togetherwithocean.TWO.Jwt.TokenDto;
import com.togetherwithocean.TWO.Member.Authority;
import com.togetherwithocean.TWO.Member.DTO.MemberJoinReq;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;


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
    public Long save(MemberJoinReq memberSave) {
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
                .authority(Authority.ROLE_USER.toString())
                .build();
        memberRepository.save(member);
        return member.getMemberNumber();
    }
 
    @Transactional
    public void updatePasswd(Member newMember) {
        memberRepository.save(newMember);
    }

    @Transactional
    public TokenDto signIn(Member loginMember) {
        // 토큰 생성
        TokenDto jwtToken = jwtProvider.generateToken(loginMember);
        return jwtToken;
    }
}
