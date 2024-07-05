package com.togetherwithocean.TWO.Certify.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

// 이메일 찾기 - 비밀번호 인증 번호 발송
@Data
@NoArgsConstructor
public class PostSendSmsReq {
    @NotEmpty(message = "전화번호를 입력해 주세요")
    String phoneNumber;

    PostSendSmsReq(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
