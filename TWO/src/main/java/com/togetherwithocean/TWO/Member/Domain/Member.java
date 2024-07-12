package com.togetherwithocean.TWO.Member.Domain;

import com.togetherwithocean.TWO.Member.Authority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.togetherwithocean.TWO.Member.Authority.ROLE_USER;

@Data
@Entity
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

    @Builder
    public Member(String realName, String nickname, String email, String passwd, String phoneNumber, String postalCode,
                String address, String detailAddress, Long charId, String charName, Long stepGoal) {
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
        this.availTrashBag = 10L;
        this.totalPlog = 0L;
        this.point = 0L;
        this.authority = ROLE_USER;
    }
}
