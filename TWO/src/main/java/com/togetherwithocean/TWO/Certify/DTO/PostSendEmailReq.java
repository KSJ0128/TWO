package com.togetherwithocean.TWO.Certify.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

// 비밀번호 찾기 - 이메일 인증 번호 발송
@Data
@NoArgsConstructor
public class PostSendEmailReq {
    @Email
    @NotEmpty(message = "이메일을 입력해 주세요")
    String email;

    PostSendEmailReq(String email) {
        this.email = email;
    }
}
