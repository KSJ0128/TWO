package com.togetherwithocean.TWO.User.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostFindUserPasswdReq {
    String realName;
    String email;
    String verifyNumber;

    @Builder
    PostFindUserPasswdReq(String realName, String email, String verifyNumber) {
        this.realName = realName;
        this.email = email;
        this.verifyNumber = verifyNumber;
    }
}