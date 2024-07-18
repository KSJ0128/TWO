package com.togetherwithocean.TWO.Member.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchChangeMypage {
    String nickname;
    String passwd;
    String rePasswd;
    String phoneNumber;
}
