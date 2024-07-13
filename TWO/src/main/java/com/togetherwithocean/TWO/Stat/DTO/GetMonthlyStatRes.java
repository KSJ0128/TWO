package com.togetherwithocean.TWO.Stat.DTO;

import com.togetherwithocean.TWO.Stat.Domain.Stat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMonthlyStatRes {
    Long monthlyPlog;
    Long monthlyScore;
    List<Stat> monthlyCalendar;
}
