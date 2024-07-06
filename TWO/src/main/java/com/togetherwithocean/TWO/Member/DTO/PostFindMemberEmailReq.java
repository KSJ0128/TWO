package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 이메일 찾기 요청
@Data
@NoArgsConstructor
public class PostFindMemberEmailReq {
    String realName;
    String phoneNumber;
    Boolean confirm;

    @Builder
    public PostFindMemberEmailReq(String realName, String phoneNumber, Boolean confirm) {
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.confirm = confirm;
    }
}