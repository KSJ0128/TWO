package com.togetherwithocean.TWO.Statistics.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "statistics")
@NoArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistics_number")
    private Long statisticsNumber;

    @Column(name = "member_number")
    private Long memberNumber;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "achive_step")
    private Boolean achiveStep;

    @Column(name = "achive_plog")
    private Boolean achivePlog;

    @Builder
    public Statistics(Long memberNumber, LocalDate date) {
        this.memberNumber = memberNumber;
        this.date = date;
        this.achiveStep = false;
        this.achivePlog = false;
    }
}
