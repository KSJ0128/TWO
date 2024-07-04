package com.togetherwithocean.TWO.Verify.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

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
