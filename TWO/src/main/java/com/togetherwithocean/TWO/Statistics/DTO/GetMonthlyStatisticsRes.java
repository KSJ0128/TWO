package com.togetherwithocean.TWO.Statistics.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMonthlyStatisticsRes {
    LocalDate date;
    Boolean plogging;
    Boolean step;
}