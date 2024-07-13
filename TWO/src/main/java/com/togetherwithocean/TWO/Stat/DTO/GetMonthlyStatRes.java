package com.togetherwithocean.TWO.Stat.DTO;

import com.togetherwithocean.TWO.Stat.Domain.Stat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class GetMonthlyStatRes {
    Long monthlyPlog;
    Long monthlyScore;
    List<Stat> monthlyCalendar;
}
