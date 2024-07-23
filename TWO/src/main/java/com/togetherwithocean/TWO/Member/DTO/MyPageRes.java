package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MyPageRes {
    String nickname;
    Long score;
    Long point;

    @Builder
    public MyPageRes(String nickname, Long score, Long point) {
        this.nickname = nickname;
        this.score = score;
        this.point = point;
    }
}

