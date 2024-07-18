package com.togetherwithocean.TWO.Stat.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class StatRes {
    Long statNumber;
    LocalDate date;
    Boolean attend;
    Long step;
    Boolean achieveStep;
    Long plogging;
    Long trashBag;
    List<String> visit;

    @Builder
    public StatRes(Long statNumber, LocalDate date, Boolean attend, Long step,
                   Boolean achieveStep, Long plogging, Long trashBag, List<String> visit)
    {
        this.statNumber = statNumber;
        this.date = date;
        this.attend = attend;
        this.step = step;
        this.achieveStep = achieveStep;
        this.plogging = plogging;
        this.trashBag = trashBag;
        this.visit = visit;
    }
}
