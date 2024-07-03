package com.togetherwithocean.TWO.User.Service;

import com.togetherwithocean.TWO.User.Domain.User;
import com.togetherwithocean.TWO.User.Repository.UserRepository;
import com.togetherwithocean.TWO.Verify.SmsUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SmsUtil smsUtil;

    @Transactional
    public User getUserByRealName(String realName) {
        return userRepository.findByRealName(realName);
    }

    @Transactional
    public String createVerifyNumber() {
        int randomNumber = (int)(Math.random() * 10000) + (int)(Math.random() * 100);
        String verifyNumber = String.valueOf(randomNumber);

        return verifyNumber;
    }
}
