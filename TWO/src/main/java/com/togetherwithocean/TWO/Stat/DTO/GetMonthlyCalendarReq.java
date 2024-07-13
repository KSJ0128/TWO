package com.togetherwithocean.TWO.Stat.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMonthlyCalendarReq {
    LocalDate startDate;
    LocalDate endDate;
}
