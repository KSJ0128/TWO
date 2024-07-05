package com.togetherwithocean.TWO.User.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 이메일 찾기 요청
@Data
@NoArgsConstructor
public class PostFindUserEmailReq {
    String realName;
    String phoneNumber;
    Boolean confirm;

    @Builder
    public PostFindUserEmailReq(String realName, String phoneNumber, Boolean confirm) {
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.confirm = confirm;
    }
}