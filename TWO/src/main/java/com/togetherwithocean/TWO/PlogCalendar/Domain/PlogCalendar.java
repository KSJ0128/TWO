package com.togetherwithocean.TWO.PlogCalendar.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "calendar")
@NoArgsConstructor
public class PlogCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_number")
    private Long calendarNumber;

    @Column(name = "member_number")
    private Long memberNumber;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "trash_bag")
    private Long trashBag;

    @Column(name = "location")
    private String location;

    @Column(name = "step")
    private Long step;

    @Builder
    public PlogCalendar(Long memberNumber, LocalDate date, Long trashBag, String location, Long step) {
        this.memberNumber = memberNumber;
        this.date = date;
        this.trashBag = trashBag;
        this.location = location;
        this.step = step;
    }
}
