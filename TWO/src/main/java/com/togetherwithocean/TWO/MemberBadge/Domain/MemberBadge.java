package com.togetherwithocean.TWO.MemberBadge.Domain;
import com.togetherwithocean.TWO.Badge.Domain.Badge;
import com.togetherwithocean.TWO.Member.Domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@Table(name = "member_badge")
@NoArgsConstructor
public class MemberBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_badge_number")
    private Long memberBadgeNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_number")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_number")
    private Badge badge;

    @Builder
    public MemberBadge(Member member, Badge badge) {
        this.member = member;
        this.badge = badge;
    }
}
