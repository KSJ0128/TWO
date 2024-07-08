package com.togetherwithocean.TWO.MemberBadge.Domain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "member_badge")
@NoArgsConstructor
public class MemberBadge {
    @Id
    @Column(name = "member_number", nullable = false)
    private Long memberNumber;

    @Id
    @Column(name = "badge_number", nullable = false)
    private Long badgeNumber;

    @Column(name = "achieve")
    private Boolean achieve;

    @Builder
    public MemberBadge(Long memberNumber, Long badgeNumber, Boolean achieve) {
        this.memberNumber = memberNumber;
        this.badgeNumber = badgeNumber;
        this.achieve = achieve;
    }
}
