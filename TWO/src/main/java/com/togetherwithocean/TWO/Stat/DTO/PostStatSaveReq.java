package com.togetherwithocean.TWO.Stat.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostStatSaveReq {
    LocalDate date;
    Long trashBag;
    String location;
    Double latitude;
    Double longtitude;
}
