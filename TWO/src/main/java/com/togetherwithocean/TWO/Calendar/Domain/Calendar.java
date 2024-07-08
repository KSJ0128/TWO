package com.togetherwithocean.TWO.Calendar.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "calendar")
@NoArgsConstructor
public class Calendar {

    @Id
    @Column(name = "member_number", nullable = false)
    private Long memberNumber;

    @Id
    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "trash_bag")
    private Long trashBag;

    @Column(name = "location")
    private String location;

    @Column(name = "step")
    private Long step;

    @Builder
    public Calendar(Long memberNumber, Date date, Long trashBag, String location, Long step) {
        this.memberNumber = memberNumber;
        this.date = date;
        this.trashBag = trashBag;
        this.location = location;
        this.step = step;
    }
}
