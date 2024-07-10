package com.togetherwithocean.TWO.PlogCalendar.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class GetMonthlyReq {
    LocalDate start;
    LocalDate end;
}
