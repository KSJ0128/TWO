package com.togetherwithocean.TWO.Member.Controller;

import com.togetherwithocean.TWO.Jwt.SecurityUtil;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import com.togetherwithocean.TWO.Jwt.TokenDto;
import com.togetherwithocean.TWO.Member.DTO.MemberJoinReq;
import com.togetherwithocean.TWO.Member.DTO.PostFindMemberEmailReq;
import com.togetherwithocean.TWO.Member.DTO.PostFindMemberPasswdReq;
import com.togetherwithocean.TWO.Member.DTO.PostSignInReq;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Service.MemberService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("member")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    public MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

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
    public ResponseEntity<String> saveBasicInfo(@RequestBody MemberJoinReq userInfoReq) {
        Long userNum = memberService.save(userInfoReq);
        if (userNum != null)
            return ResponseEntity.status(HttpStatus.OK).body("회원 가입 완료 " + userNum);
        return  ResponseEntity.status(HttpStatus.OK).body("회원가입 실패");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> sign_in(@RequestBody PostSignInReq postSignInReq, HttpServletResponse response) {

        // 로그인 요청 보낸 멤버 정보
        Member loginMember = memberRepository.findMemberByEmail(postSignInReq.getEmail());

        // 유효하지 않은 로그인 요청인 경우
        if (loginMember == null && loginMember.getPasswd().equals(postSignInReq.getPasswd()))
            return ResponseEntity.status(HttpStatus.OK).body(null);

        // 액세스 토큰 발급
        TokenDto token = memberService.signIn(loginMember);
        log.info("request email = {}, password = {}", loginMember.getEmail(), loginMember.getPasswd());
        log.info("jwtToken accessToken = {}, refreshToken = {}", token.getAccessToken(), token.getRefreshToken());


        // Header에 정보 넘겨주기
        response.setHeader("MemberEmail", loginMember.getEmail());
        response.setHeader("TokenType", "Bearer");
        response.setHeader("AccessToken", token.getAccessToken());
        response.setHeader("RefreshToken", token.getRefreshToken());

        // 로그인 성공시
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testToken() {
        return ResponseEntity.status(HttpStatus.OK).body(SecurityUtil.getCurrentEmail());
    }
}