package com.togetherwithocean.TWO.Statistics.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GetDailyStatisticsRes {
    LocalDate date;
    Long plogging;
    Long step;
}
