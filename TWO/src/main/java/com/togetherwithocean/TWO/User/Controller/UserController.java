package com.togetherwithocean.TWO.User.Controller;

import com.togetherwithocean.TWO.User.DTO.PostFindUserEmailReq;
import com.togetherwithocean.TWO.User.Domain.User;
import com.togetherwithocean.TWO.User.Service.UserService;
import com.togetherwithocean.TWO.Verify.SmsDao;
import com.togetherwithocean.TWO.Verify.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final SmsDao smsDao;

    @Autowired
    public UserController(UserService userService, SmsUtil smsUtil, SmsDao smsDao) {
        this.userService = userService;
        this.smsDao = smsDao;
    }

    @PostMapping("/findEmail")
    public ResponseEntity<String> findUserEmail(@RequestBody PostFindUserEmailReq postFindUserEmailReq) {

        User findUser = userService.getUserByRealName(postFindUserEmailReq.getRealName()); // 유저명으로 유저 조회

        // DB 상에 해당 유저가 존재하는지 확인
        if (findUser == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 유저 정보입니다.");

        // DB 상의 유저 정보와 일치하는지 확인 - 유저명과 유저 번호가 같은 유저 가리키는지
        if (!findUser.getPhoneNumber().equals(postFindUserEmailReq.getPhoneNumber()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 유저 정보입니다.");

        // redis에서 키 값에 해당하는 전화번호 통해 생성된 인증번호 불러옴
        String verifyNumber = smsDao.getSmsCertification(postFindUserEmailReq.getPhoneNumber());

        // 유저가 입력한 인증번호와 redis 상의 인증번호 대조
        if (!postFindUserEmailReq.getVerifyNumber().equals(verifyNumber))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 인증 번호입니다.");

        return ResponseEntity.status(HttpStatus.OK).body("이메일은 " + findUser.getEmail() + "입니다.");
    }
}