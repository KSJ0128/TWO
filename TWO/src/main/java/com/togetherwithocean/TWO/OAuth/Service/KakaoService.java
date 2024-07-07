package com.togetherwithocean.TWO.OAuth.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.togetherwithocean.TWO.OAuth.DTO.KakaoAccessTokenDTO;
import com.togetherwithocean.TWO.OAuth.DTO.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.swing.plaf.IconUIResource;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private String redirectUri = "http://localhost:8080/oauth/kakao/callback";
    private String clientId = "4f70d41bf691ab5b26944b067390995b";
    private final ObjectMapper objectMapper;


    @Transactional
    public String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Request에 담을 정보 추가
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        // request를 하기위해 HttpEntity 객체에 헤더와 정보 조립
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);

        // code에 대한 인증요청을 할 url
        String url = "https://kauth.kakao.com/oauth/token";

        // code에 대한 인증요청을 보낸뒤 결과를 받아 response 객체에 담아 받는다.
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        String accessToken = null;
        try {
            accessToken = objectMapper.readValue(response.getBody(), KakaoAccessTokenDTO.class).getAccess_token();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return accessToken;
    }

    @Transactional
    public KakaoUserInfo getUserInfoByAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        // Request에 담을 정보 추가
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);

        // 유저 정보를 받기위해 요청할 kakao 서버 url
        String url = "https://kapi.kakao.com/v2/user/me";
        String info = restTemplate.postForObject(url, request, String.class);
        try {
            JsonNode jsonNode = objectMapper.readTree(info);
            String email = String.valueOf(jsonNode.get("kakao_account").get("email"));
            String emailResult = email.substring(1, email.length() - 1);
            String name = String.valueOf(jsonNode.get("kakao_account").get("name"));
            String nameResult = name.substring(1, name.length() - 1);
            String phoneNumber = String.valueOf(jsonNode.get("kakao_account").get("phone_number"));
            String phoneNumberResult = "0" + phoneNumber.substring(1, phoneNumber.length() - 1).replace("+82 ", "");

            KakaoUserInfo kakaoUserInfo = KakaoUserInfo.builder()
                    .name(nameResult)
                    .email(emailResult)
                    .phoneNumber(phoneNumberResult)
                    .build();
            return kakaoUserInfo;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
