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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final SmsUtil smsUtil;
    private final SmsDao smsDao;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/findEmail")
    public ResponseEntity<String> findUserEmail(PostFindUserEmailReq postFindUserEmailReq) {
        User findUser = userService.getUserByRealName(postFindUserEmailReq.getRealName());
        if (findUser == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 유저 정보입니다.");
        if (!findUser.getPhoneNumber().equals(postFindUserEmailReq.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 유저 정보입니다.");
        }
        String verifyNumber = smsDao.getSmsCertification(postFindUserEmailReq.getPhoneNumber());
        if (!postFindUserEmailReq.getVerifyNumber().equals(verifyNumber))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 인증 번호입니다.");
        return ResponseEntity.status(HttpStatus.OK).body("이메일은 " + findUser.getEmail() + "입니다.");
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendSmsToFindEmail(String phoneNumber) {
        try {
            String verifyNumber = userService.createVerifyNumber(); // 인증번호 생성
            smsUtil.sendSms(phoneNumber, verifyNumber); // 인증번호 발송
            smsDao.createSmsCertification(phoneNumber, verifyNumber); // 인증번호 비교

            return ResponseEntity.status(HttpStatus.OK).body("인증 번호를 발송했습니다.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 번호를 발송을 실패했습니다.");
        }
    }
}