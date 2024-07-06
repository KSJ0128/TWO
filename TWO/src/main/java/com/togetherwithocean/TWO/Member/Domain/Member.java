package com.togetherwithocean.TWO.Member.Domain;

import com.togetherwithocean.TWO.Member.Authority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.togetherwithocean.TWO.Member.Authority.ROLE_USER;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_number")
    private Long memberNumber;

    @Column(name = "real_name")
    private String realName;

    private String nickname;

    private String email;

    private String passwd;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "postal_code")
    private String postalCode;

    private String address;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "char_id")
    private Long charId;

    @Column(name = "char_name")
    private String charName;

    @Column(name = "step_goal")
    private Long stepGoal;

    @Column(name = "daily_step")
    private Long dailyStep;

    @Column(name = "total_plogging")
    private Long totalPlogging;

    @Column(name = "trash_bag")
    private Long trashBag;

    private Long score;

    private Long point;

    private String authority;


    @Builder
    public Member(String realName, String nickname, String email, String passwd, String phoneNumber, String postalCode,
                String address, String detailAddress, Long charId, String charName, Long stepGoal, String authority) {
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
        this.dailyStep = 0L;
        this.totalPlogging = 0L;
        this.trashBag = 10L;
        this.score = 0L;
        this.point = 0L;
        this.authority = authority;
    }
}
