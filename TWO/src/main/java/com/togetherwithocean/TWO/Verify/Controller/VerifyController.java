package com.togetherwithocean.TWO.Verify.Controller;

import com.togetherwithocean.TWO.Verify.DTO.PostSendEmailReq;
import com.togetherwithocean.TWO.Verify.DTO.PostSendSmsReq;
import com.togetherwithocean.TWO.Verify.Service.VerifyService;
import com.togetherwithocean.TWO.Verify.Config.SmsConfig;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("verify")
public class VerifyController {
    private final VerifyService verifyService;
    private final SmsConfig smsUtil;

    @Autowired
    public VerifyController(VerifyService verifyService, SmsConfig smsUtil) {
        this.verifyService = verifyService;
        this.smsUtil = smsUtil;
    }

    @PostMapping("/sendSms")
    public ResponseEntity<String> sendSmsToFindEmail(@RequestBody PostSendSmsReq postSendSmsReq) {
        try {
            String verifyNumber = verifyService.createVerifyNumber(); // 인증번호 생성
            smsUtil.sendSms(postSendSmsReq.getPhoneNumber(), verifyNumber); // 인증번호 발송
            verifyService.createSmsCertification(postSendSmsReq.getPhoneNumber(), verifyNumber); // 인증번호 redis에 저장
            return ResponseEntity.status(HttpStatus.OK).body("인증 번호를 발송했습니다.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 번호를 발송을 실패했습니다.");
        }
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmailToFindPasswd(@RequestBody @Valid PostSendEmailReq postSendEmailReq) {
        try {
            String verifyNumber = verifyService.createVerifyNumber(); // 인증번호 생성
            verifyService.joinEmail(postSendEmailReq.getEmail(), verifyNumber);
            verifyService.createEmailCertification(postSendEmailReq.getEmail(), verifyNumber); // 인증번호 redis에 저장
            return ResponseEntity.status(HttpStatus.OK).body("인증 번호를 발송했습니다.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 번호를 발송을 실패했습니다.");
        }
    }
}
