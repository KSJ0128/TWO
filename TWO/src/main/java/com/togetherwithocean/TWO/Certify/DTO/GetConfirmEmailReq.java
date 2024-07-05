package com.togetherwithocean.TWO.Certify.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

// 비밀번호 찾기 - 이메일 인증 번호 확인
@Data
@NoArgsConstructor
public class GetConfirmEmailReq {
    String email;
    String certifyNumber;

    GetConfirmEmailReq(String email, String certifyNumber) {
        this.email = email;
        this.certifyNumber = certifyNumber;
    }
}
