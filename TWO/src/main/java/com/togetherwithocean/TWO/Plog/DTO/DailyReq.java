package com.togetherwithocean.TWO.Plog.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DailyReq {
    LocalDate date;

    @Builder
    public DailyReq(LocalDate date) {
        this.date = date;
    }
}
