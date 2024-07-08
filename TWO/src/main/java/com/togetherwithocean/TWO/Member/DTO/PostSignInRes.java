package com.togetherwithocean.TWO.Member.DTO;

import com.togetherwithocean.TWO.Jwt.TokenDto;
import com.togetherwithocean.TWO.Member.Domain.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostSignInRes {
    Member member;
    TokenDto token;

    @Builder
    public PostSignInRes(Member member, TokenDto token) {
        this.member = member;
        this.token = token;
    }
}
