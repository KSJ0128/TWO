package com.togetherwithocean.TWO.Plog.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "plog")
@NoArgsConstructor
public class Plog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plog_number")
    private Long plogNumber;

    @Column(name = "member_number")
    private Long memberNumber;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "trash_bag")
    private Long trashBag;

    @Column(name = "location")
    private String location;

    @Builder
    public Plog(Long memberNumber, LocalDate date, Long trashBag, String location) {
        this.memberNumber = memberNumber;
        this.date = date;
        this.trashBag = trashBag;
        this.location = location;
    }
}
