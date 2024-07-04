package com.togetherwithocean.TWO.User.Service;

import com.togetherwithocean.TWO.User.Domain.User;
import com.togetherwithocean.TWO.User.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User getUserByRealName(String realName) {
        return userRepository.findByRealName(realName);
    }

}
