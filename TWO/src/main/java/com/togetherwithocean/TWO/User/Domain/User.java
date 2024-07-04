package com.togetherwithocean.TWO.User.Domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_number")
    private Long userNumber;

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

    @Column(name = "daily_step")
    private Long dailyStep;

    @Column(name = "total_plogging")
    private Long totalPlogging;

    @Column(name = "trash_bag")
    private Long trashBag;

    @Column(name = "score")
    private Long score;

    @Column(name = "point")
    private Long point;

    @Builder
    public User(String realName, String nickname, String email, String passwd, String phoneNumber, String postalCode,
                String address, String detailAddress, Long charId, String charName, Long stepGoal, Long dailyStep,
                Long totalPlogging, Long trashBag, Long score, Long point) {
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
        this.dailyStep = dailyStep;
        this.totalPlogging = totalPlogging;
        this.trashBag = trashBag;
        this.score = score;
        this.point = point;
    }
}
