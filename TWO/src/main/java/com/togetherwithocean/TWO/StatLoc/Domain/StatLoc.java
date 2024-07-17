package com.togetherwithocean.TWO.StatLoc.Domain;

import com.togetherwithocean.TWO.Visit.Domain.Visit;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "stat_loc")
@NoArgsConstructor
public class StatLoc {
    @Id
    @Column(name = "stat_loc_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statLocNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_stat_number")
    private Stat stat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_visit_number")
    private Visit visit;
}
