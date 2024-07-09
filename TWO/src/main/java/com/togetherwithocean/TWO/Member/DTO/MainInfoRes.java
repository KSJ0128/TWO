package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainInfoRes {
    String nickname;
    Long charId;
    String charName;
    Long stepGoal;
    Long dailyStep;
    Long monthlyTrashBag;

    @Builder
    public MainInfoRes(String nickname, Long charId, String charName, Long stepGoal, Long dailyStep, Long monthlyTrashBag) {
        this.nickname = nickname;
        this.charId = charId;
        this.charName = charName;
        this.stepGoal = stepGoal;
        this.dailyStep = dailyStep;
        this.monthlyTrashBag = monthlyTrashBag;
    }
}
