package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostSignInReq {
    String email;
    String passwd;

    @Builder
    PostSignInReq(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }
}
