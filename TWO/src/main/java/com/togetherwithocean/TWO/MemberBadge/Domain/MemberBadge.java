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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_badge_number")
    private Long memberBadgeNumber;

    @Column(name = "member_number")
    private Long memberNumber;

    @Column(name = "badge_number")
    private Long badgeNumber;

    @Builder
    public MemberBadge(Long memberNumber, Long badgeNumber) {
        this.memberNumber = memberNumber;
        this.badgeNumber = badgeNumber;
    }
}
