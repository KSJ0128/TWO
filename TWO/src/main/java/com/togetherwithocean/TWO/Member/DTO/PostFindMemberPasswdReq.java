package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 비밀번호 찾기 요청
@Data
@NoArgsConstructor
public class PostFindMemberPasswdReq {
    String realName;
    String email;
    String passwd;
    String re_passwd;

    @Builder
    PostFindMemberPasswdReq(String realName, String email, String passwd, String re_passwd) {
        this.realName = realName;
        this.email = email;
        this.passwd = passwd;
        this.re_passwd = re_passwd;
    }
}