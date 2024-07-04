package com.togetherwithocean.TWO.Verify.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostSendSmsReq {
    @NotEmpty(message = "전화번호를 입력해 주세요")
    String phoneNumber;

    PostSendSmsReq(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
