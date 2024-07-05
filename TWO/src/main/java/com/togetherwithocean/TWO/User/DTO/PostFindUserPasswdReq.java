package com.togetherwithocean.TWO.User.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 비밀번호 찾기 요청
@Data
@NoArgsConstructor
public class PostFindUserPasswdReq {
    String realName;
    String email;
    String passwd;
    String re_passwd;
    Boolean confirm;

    @Builder
    PostFindUserPasswdReq(String realName, String email, String passwd, String re_passwd, Boolean confirm) {
        this.realName = realName;
        this.email = email;
        this.passwd = passwd;
        this.re_passwd = re_passwd;
        this.confirm = confirm;
    }
}