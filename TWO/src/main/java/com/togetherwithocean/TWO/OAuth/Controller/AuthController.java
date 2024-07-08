package com.togetherwithocean.TWO.OAuth.Controller;


import com.togetherwithocean.TWO.Jwt.TokenDto;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Member.Service.MemberService;
import com.togetherwithocean.TWO.OAuth.DTO.KakaoUserInfo;
import com.togetherwithocean.TWO.OAuth.Service.KakaoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class AuthController {
    private String redirectUri = "http://localhost:8080/oauth/kakao/callback";
    private String clientId = "4f70d41bf691ab5b26944b067390995b";
    private final KakaoService kakaoService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/kakao/login")
    public ResponseEntity<String> kakaoLogin() {
        String kakaoLoginUrl =
                "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId
                        + "&redirect_uri=" + redirectUri + "&response_type=code";
        return ResponseEntity.status(HttpStatus.OK).body(kakaoLoginUrl);
    }

    @GetMapping("/kakao/callback")
    public @ResponseBody ResponseEntity<Member> kakaoCallback(@RequestParam String code, HttpServletResponse response) {
        String accessToken = kakaoService.getAccessToken(code);
        KakaoUserInfo kakaoUserInfo = kakaoService.getUserInfoByAccessToken(accessToken);
//        System.out.println(kakaoUserInfo.getName());
//        System.out.println(kakaoUserInfo.getEmail());
//        System.out.println(kakaoUserInfo.getPhoneNumber());

        // 카카오에서 가져온 정보 바탕으로 사용자 찾기
        Member loginMember = memberRepository.findMemberByEmail(kakaoUserInfo.getEmail());

        if (loginMember == null)
            return ResponseEntity.status(HttpStatus.OK).body(null);

        // 토큰 생성 및 헤더에 토큰 정보 추가
        TokenDto token = memberService.setTokenInHeader(loginMember, response);

        // 로그인 성공시 사용자 반환
        return ResponseEntity.status(HttpStatus.OK).body(loginMember);
    }
}
