package com.togetherwithocean.TWO.Member.DTO;

import com.togetherwithocean.TWO.MemberItem.DTO.MemberItemDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MainInfoRes {
    String nickname;
    Long charId;
    String charName;
    Long stepGoal;
    Long dailyStep;
    Long monthlyPlog;
    List<MemberItemDTO> equipItemList;

    @Builder
    public MainInfoRes(String nickname, Long charId, String charName, Long stepGoal, Long dailyStep, Long monthlyPlog, List<MemberItemDTO> equipItemList) {
        this.nickname = nickname;
        this.charId = charId;
        this.charName = charName;
        this.stepGoal = stepGoal;
        this.dailyStep = dailyStep;
        this.monthlyPlog = monthlyPlog;
        this.equipItemList = equipItemList;
    }
}
