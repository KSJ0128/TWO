package com.togetherwithocean.TWO.Verify.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VerifyService {

    @Transactional
    public String createVerifyNumber() {
        int randomNumber = (int)(Math.random() * 10000) + (int)(Math.random() * 100);

        return String.valueOf(randomNumber);
    }
}
