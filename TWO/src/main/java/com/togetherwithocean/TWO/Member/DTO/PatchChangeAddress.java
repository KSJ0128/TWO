package com.togetherwithocean.TWO.Member.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;

@Data
@NoArgsConstructor
public class PatchChangeAddress {
    String postalCode;
    String address;
    String detailAddress;

    @Builder
    PatchChangeAddress(String postalCode, String address, String detailAddress) {
        this.postalCode = postalCode;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
