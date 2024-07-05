package com.togetherwithocean.TWO.Certify.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

// 이메일 찾기 - 문자 인증 번호 확인
@Data
@NoArgsConstructor
public class GetConfirmSmsReq {
    String phoneNumber;
    String certifyNumber;

    GetConfirmSmsReq(String phoneNumber, String certifyNumber) {
        this.phoneNumber = phoneNumber;
        this.certifyNumber = certifyNumber;
    }
}
