package com.togetherwithocean.TWO.OAuth.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfo {
    private String name;
    private String email;
    private String phoneNumber;

    @Builder
    public KakaoUserInfo(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}