package com.togetherwithocean.TWO.Plog.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GetMonthlyRes {
    LocalDate date;
    Long dailyStep;
    Boolean plogging;
    Boolean step;

    public GetMonthlyRes(LocalDate date) {
        this.date = date;
        this.dailyStep = 0L;
        this.plogging = false;
        this.step = false;
    }
}