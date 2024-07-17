package com.togetherwithocean.TWO.Member.Controller;

import com.togetherwithocean.TWO.Jwt.SecurityUtil;
import com.togetherwithocean.TWO.Member.DTO.*;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Ranking.Service.RankingService;
import com.togetherwithocean.TWO.Stat.Service.StatService;
import jakarta.servlet.http.HttpServletResponse;
import com.togetherwithocean.TWO.Jwt.TokenDto;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;


@Controller
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final StatService statService;
    private final RankingService rankingService;

    // 이메일 찾기 api
    @PostMapping("/find-email")
    public ResponseEntity<String> findMemberEmail(@RequestBody PostFindMemberEmailReq postFindMemberEmailReq) {

        Member findMember = memberService.getMemberByRealName(postFindMemberEmailReq.getRealName()); // 유저명으로 유저 조회

        // DB 상에 해당 유저가 존재하는지 확인
        if (findMember == null)
            return ResponseEntity.status(HttpStatus.OK).body("가입된 유저가 아닙니다.");

        // DB 상의 유저 정보와 일치하는지 확인 - 유저명과 유저 번호가 같은 유저 가리키는지
        if (!findMember.getPhoneNumber().equals(postFindMemberEmailReq.getPhoneNumber()))
            return ResponseEntity.status(HttpStatus.OK).body("가입된 유저가 아닙니다.");

        // 인증 번호 확인 유무 체크
        if (postFindMemberEmailReq.getConfirm())
            return ResponseEntity.status(HttpStatus.OK).body(findMember.getNickname() + "님의 이메일은 " + findMember.getEmail() + " 입니다.");
        else
            return ResponseEntity.status(HttpStatus.OK).body("인증 번호 확인이 되지 않았습니다.");
    }

    // 비밀번호 찾기 api
    @PatchMapping("/find-pw")
    public ResponseEntity<String> findUserPasswd(@RequestBody PostFindMemberPasswdReq postFindMemberPasswdReq) {

        Member findMember = memberService.getMemberByRealName(postFindMemberPasswdReq.getRealName()); // 유저명으로 유저 조회

        // DB 상에 해당 유저가 존재하는지 확인
        if (findMember == null)
            return ResponseEntity.status(HttpStatus.OK).body("가입된 유저가 아닙니다.");

        // DB 상의 유저 정보와 일치하는지 확인 - 유저명과 유저 이메일이 같은 유저 가리키는지
        if (!findMember.getEmail().equals(postFindMemberPasswdReq.getEmail()))
            return ResponseEntity.status(HttpStatus.OK).body("가입된 유저가 아닙니다.");

        // 인증 번호 확인 유무 체크
        if (!postFindMemberPasswdReq.getConfirm())
            return ResponseEntity.status(HttpStatus.OK).body("인증 번호 확인이 되지 않았습니다.");

        // 비밀번호 일치 확인
        if (postFindMemberPasswdReq.getPasswd().equals(postFindMemberPasswdReq.getRe_passwd())) {
            findMember.setPasswd(postFindMemberPasswdReq.getPasswd());
            memberService.updatePasswd(findMember);
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호 설정이 완료되었습니다.");
        }
        else
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 일치하지 않습니다.");
    }

    @GetMapping("/check-nick")
    public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
        boolean isDuplicate = memberService.isNicknameDuplicate(nickname);
        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.OK).body("이미 사용중인 닉네임입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("사용가능한 닉네임입니다.");
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        boolean isDuplicate = memberService.isEmailDuplicate(email);
        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.OK).body("이미 사용중인 이메일입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("사용가능한 이메일입니다.");
        }
    }

    @PostMapping("/join")
    public ResponseEntity<Member> saveBasicInfo(@RequestBody MemberJoinReq userInfoReq) {
        Member joinMember = memberService.save(userInfoReq);

        statService.makeNewStat(joinMember, LocalDate.now()); // 당일 stat 생성
        rankingService.makeNewRanking(joinMember); // 랭킹 정보 등록
        return ResponseEntity.status(HttpStatus.OK).body(joinMember);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<PostSignInRes> sign_in(@RequestBody PostSignInReq postSignInReq, HttpServletResponse response) {

        // 로그인 요청 보낸 멤버 정보
        Member loginMember = memberRepository.findMemberByEmail(postSignInReq.getEmail());

        // 유효하지 않은 로그인 요청인 경우
        if (loginMember == null || !loginMember.getPasswd().equals(postSignInReq.getPasswd()))
            return ResponseEntity.status(HttpStatus.OK).body(null);

        // 토큰 생성 및 헤더에 토큰 정보 추가
        TokenDto token = memberService.setTokenInHeader(loginMember, response);

        // 로그인 성공시
        return ResponseEntity.status(HttpStatus.OK).body(memberService.setSignInInfo(loginMember, token));
    }

    @GetMapping("/main-info")
    public ResponseEntity<MainInfoRes> viewMainInfo(Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        MainInfoRes mainInfo = memberService.getMainInfo(principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(mainInfo);
    }

    @PatchMapping("/address")
    public ResponseEntity<Member> changeAddress(@RequestBody PatchChangeAddress patchChangeAddress, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Member member = memberRepository.findMemberByEmail(principal.getName());
        memberService.changeAddress(member, patchChangeAddress);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @PatchMapping("/step-goal")
    public ResponseEntity<Member> changeStepGoal(@RequestBody PatchChangeStepGoal patchChangeStepGoal, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Member member = memberRepository.findMemberByEmail(principal.getName());
        memberService.changeStepGoal(member, patchChangeStepGoal.getStepGoal());
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

//    @GetMapping("/test")
//    public ResponseEntity<String> testToken() { return ResponseEntity.status(HttpStatus.OK).body(SecurityUtil.getCurrentEmail()); }
}