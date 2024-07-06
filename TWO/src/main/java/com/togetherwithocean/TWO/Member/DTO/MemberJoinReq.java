package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberJoinReq {
    String realName;
    String nickname;
    String email;
    String passwd;
    String checkPasswd;
    String phoneNumber;
    String postalCode;
    String address;
    String detailAddress;
    Long   charId;
    String charName;
    Long   stepGoal;

    @Builder
    public MemberJoinReq(String realName, String email, String passwd, String checkPasswd, String phoneNumber,
                         String postalCode, String address, String detailAddress,
                         Long charId, String charName, Long stepGoal)
    {
        this.realName = realName;
        this.email = email;
        this.passwd = passwd;
        this.checkPasswd = checkPasswd;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.charId = charId;
        this.charName = charName;
        this.stepGoal = stepGoal;
    }
}
