package com.togetherwithocean.TWO.User.Service;

import com.togetherwithocean.TWO.User.DTO.UserJoinReq;
import com.togetherwithocean.TWO.User.Domain.User;
import com.togetherwithocean.TWO.User.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 유저명으로 유저 찾기
    @Transactional
    public User getUserByRealName(String realName) {
        return userRepository.findByRealName(realName);
    }


    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public Long save(UserJoinReq userSave) {
        System.out.println(userSave.getPasswd() + " "+ userSave.getCheckPasswd());

        // 비밀번호 일치 여부 예외처리?
        if (!userSave.getPasswd().equals(userSave.getCheckPasswd()))
            return null; // 비밀번호가 일치하지 않습니다

        System.out.println("비밀번호 일치?");
        User user = User.builder()
                .realName(userSave.getRealName())
                .nickname(userSave.getNickname())
                .email(userSave.getEmail())
                .passwd(userSave.getPasswd())
                .phoneNumber(userSave.getPhoneNumber())
                .postalCode(userSave.getPostalCode())
                .address(userSave.getAddress())
                .detailAddress(userSave.getDetailAddress())
                .charId(userSave.getCharId())
                .charName(userSave.getCharName())
                .stepGoal(userSave.getStepGoal())
                .build();
        userRepository.save(user);
        return user.getUserNumber();
    }
 
    @Transactional
    public void updatePasswd(User newUser) {
        userRepository.save(newUser);
    }
}
