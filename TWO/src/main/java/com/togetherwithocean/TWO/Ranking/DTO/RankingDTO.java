package com.togetherwithocean.TWO.Ranking.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RankingDTO {
    Long rank;
    String name;
    Long score;

    @Builder
    public RankingDTO(Long rank, String name, Long score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }
}
