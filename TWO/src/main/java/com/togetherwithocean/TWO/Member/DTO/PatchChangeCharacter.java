package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchChangeCharacter {
    Long charId;

    @Builder
    PatchChangeCharacter(Long charId) {
        this.charId = charId;
    }
}
