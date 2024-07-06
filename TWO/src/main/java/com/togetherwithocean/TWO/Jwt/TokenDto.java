package com.togetherwithocean.TWO.Jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    String grantType;
    String accessToken;
    String refreshToken;
    Long userNumber;
}
