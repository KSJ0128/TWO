package com.togetherwithocean.TWO.Visit.Domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import com.togetherwithocean.TWO.StatLoc.Domain.StatLoc;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "visit")
@NoArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_number")
    private Long visitNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "recommend")
    private Boolean recommend;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longtitude")
    private Double longtitude;

    @OneToMany(mappedBy = "visit")
    private List<StatLoc> statList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_member_number")
    @JsonBackReference
    private Member member;

    @Builder
    public Visit(String name, LocalDate date, Boolean recommend, Double latitude, Double longtitude, Member member) {
        this.name = name;
        this.date = date;
        this.recommend = recommend;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.member = member;
    }
}

