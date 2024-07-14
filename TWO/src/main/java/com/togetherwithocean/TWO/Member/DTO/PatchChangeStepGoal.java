package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchChangeStepGoal {
    Long stepGoal;

    @Builder
    PatchChangeStepGoal(Long stepGoal) {
        this.stepGoal = stepGoal;
    }
}
