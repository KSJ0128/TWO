package com.togetherwithocean.TWO.Stat.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatchStatWalkReq {
    Long step;
    LocalDate date;
}
