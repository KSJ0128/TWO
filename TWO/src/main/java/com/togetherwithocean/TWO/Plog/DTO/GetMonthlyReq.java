package com.togetherwithocean.TWO.Plog.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GetMonthlyReq {
    LocalDate start;
    LocalDate end;
}
