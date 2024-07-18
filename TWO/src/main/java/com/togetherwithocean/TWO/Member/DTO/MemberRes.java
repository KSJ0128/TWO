package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberRes {
    String realName;
    String nickname;
    String email;
    String passwd;
    String phoneNumber;
    String postalCode;
    String address;
    String detailAddress;
    Long charId;
    String charName;
    Long stepGoal;
    Long availTrashBag;
    Long totalPlog;
    Long point;

    @Builder
    MemberRes(String realName, String nickname, String email, String passwd, String phoneNumber, String postalCode,
              String address, String detailAddress, Long charId, String charName, Long stepGoal, Long availTrashBag,
              Long totalPlog, Long point)
    {
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
        this.availTrashBag = availTrashBag;
        this.totalPlog = totalPlog;
        this.point = point;
    }
}
