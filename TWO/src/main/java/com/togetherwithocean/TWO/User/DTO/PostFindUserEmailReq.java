package com.togetherwithocean.TWO.User.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostFindUserEmailReq {
    String realName;
    String phoneNumber;
    String verifyNumber;

    @Builder
    public PostFindUserEmailReq(String realName, String phoneNumber, Long verifyNumber) {
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.verifyNumber = verifyNumber;
    }
}
