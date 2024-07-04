package com.togetherwithocean.TWO.Verify.Controller;

import com.togetherwithocean.TWO.Verify.Service.VerifyService;
import com.togetherwithocean.TWO.Verify.SmsDao;
import com.togetherwithocean.TWO.Verify.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("verify")
public class VerifyController {
    private final VerifyService verifyService;
    private final SmsUtil smsUtil;
    private final SmsDao smsDao;

    @Autowired
    public VerifyController(VerifyService verifyService, SmsUtil smsUtil, SmsDao smsDao) {
        this.verifyService = verifyService;
        this.smsUtil = smsUtil;
        this.smsDao = smsDao;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendSmsToFindEmail(@RequestParam String phoneNumber) {
        try {
            String verifyNumber = verifyService.createVerifyNumber(); // 인증번호 생성
            smsUtil.sendSms(phoneNumber, verifyNumber); // 인증번호 발송
            smsDao.createSmsCertification(phoneNumber, verifyNumber); // 인증번호 redis에 저장
            return ResponseEntity.status(HttpStatus.OK).body("인증 번호를 발송했습니다.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 번호를 발송을 실패했습니다.");
        }
    }
}
