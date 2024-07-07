package com.togetherwithocean.TWO.OAuth.Controller;


import com.togetherwithocean.TWO.OAuth.DTO.KakaoUserInfo;
import com.togetherwithocean.TWO.OAuth.Service.KakaoService;
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

    @GetMapping("/kakao/login")
    public ResponseEntity<String> kakaoLogin() {
        String kakaoLoginUrl =
                "https://kauth.kakao.com/oauth/authorize?client_id=" + clientId
                        + "&redirect_uri=" + redirectUri + "&response_type=code";
        return ResponseEntity.status(HttpStatus.OK).body(kakaoLoginUrl);
    }

    @GetMapping("/kakao/callback")
    public @ResponseBody ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        String accessToken = kakaoService.getAccessToken(code);
        KakaoUserInfo kakaoUserInfo = kakaoService.getUserInfoByAccessToken(accessToken);
        System.out.println(kakaoUserInfo.getName());
        System.out.println(kakaoUserInfo.getEmail());
        System.out.println(kakaoUserInfo.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.OK).body("유저 정보 " + kakaoUserInfo.toString());
    }
}
