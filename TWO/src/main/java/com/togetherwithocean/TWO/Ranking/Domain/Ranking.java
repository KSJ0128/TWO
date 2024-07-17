package com.togetherwithocean.TWO.Ranking.Domain;

import com.togetherwithocean.TWO.Member.Domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ranking")
@NoArgsConstructor
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_number")
    private Long rankingNumber;

    @Column(name = "score")
    private Long score;

    @Builder
    public Ranking(Member member) {
        this.score = 0L;
    }
}
