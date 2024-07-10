package com.togetherwithocean.TWO.PlogCalendar.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class MonthlyDto {
    LocalDate date;
    Long plogging;
    Long step;

    public MonthlyDto(LocalDate date, Long plogging, Long step) {
        this.date = date;
        this.plogging = plogging;
        this.step = step;
    }
}
