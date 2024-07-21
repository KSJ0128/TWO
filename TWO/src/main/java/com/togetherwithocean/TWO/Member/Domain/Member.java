package com.togetherwithocean.TWO.Member.Domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.togetherwithocean.TWO.Member.Authority;
import com.togetherwithocean.TWO.MemberBadge.Domain.MemberBadge;
import com.togetherwithocean.TWO.MemberItem.Domain.MemberItem;
import com.togetherwithocean.TWO.Ranking.Domain.Ranking;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import com.togetherwithocean.TWO.StatLoc.Domain.StatLoc;
import com.togetherwithocean.TWO.Visit.Domain.Visit;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.togetherwithocean.TWO.Member.Authority.ROLE_USER;

@Entity
@Getter @Setter
@Table(name = "member")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_number")
    private Long memberNumber;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address")
    private String address;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "char_id")
    private Long charId;

    @Column(name = "char_name")
    private String charName;

    @Column(name = "step_goal")
    private Long stepGoal;

    @Column(name = "avail_trash_bag")
    private Long availTrashBag;

    @Column(name = "total_plog")
    private Long totalPlog;

    @Column(name = "point")
    private Long point;

    @Column(name = "authority")
    private Authority authority;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<MemberBadge> badgesList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<MemberItem> itemsList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Stat> statList = new ArrayList<>();

//    @OneToOne
//    @JoinColumn(name = "ranking_ranking_number", unique = true)
//    private Ranking ranking;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // CascadeType.ALL을 사용하여 Ranking 엔티티가 자동으로 저장되도록 함
    @JoinColumn(name = "ranking_ranking_number", unique = true)
    private Ranking ranking;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Visit> visitList = new ArrayList<>();

    @Builder
    public Member(String realName, String nickname, String email, String passwd, String phoneNumber, String postalCode,
                String address, String detailAddress, Long charId, String charName, Long stepGoal, Ranking ranking) {
        this.realName = realName;
        this.nickname = nickname;
        this.email = email;
        this.passwd = passwd;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.charId = charId;
        this.charName = charName;
        this.stepGoal = stepGoal;
        this.availTrashBag = 0L;
        this.totalPlog = 0L;
        this.point = 0L;
        this.ranking = ranking;
        this.authority = ROLE_USER;
    }
}
