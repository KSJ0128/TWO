package com.togetherwithocean.TWO.Certify.Controller;

import com.togetherwithocean.TWO.Certify.DTO.GetConfirmEmailReq;
import com.togetherwithocean.TWO.Certify.DTO.GetConfirmSmsReq;
import com.togetherwithocean.TWO.Certify.DTO.PostSendEmailReq;
import com.togetherwithocean.TWO.Certify.DTO.PostSendSmsReq;
import com.togetherwithocean.TWO.Certify.Service.CertifyService;
import com.togetherwithocean.TWO.Certify.Config.SmsConfig;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("certify")
public class CertifyController {
    private final CertifyService certifyService;
    private final SmsConfig smsUtil;

    @Autowired
    public CertifyController(CertifyService certifyService, SmsConfig smsUtil) {
        this.certifyService = certifyService;
        this.smsUtil = smsUtil;
    }

    // 인증 번호 문자 발송 api
    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSmsToFindEmail(@RequestBody PostSendSmsReq postSendSmsReq) {
        try {
            // 인증 번호 생성
            String verifyNumber = certifyService.createVerifyNumber();

            // 인증 번호 발송
            smsUtil.sendSms(postSendSmsReq.getPhoneNumber(), verifyNumber);

            // 인증 번호 redis에 3분 간 저장
            certifyService.createSmsCertification(postSendSmsReq.getPhoneNumber(), verifyNumber);

            return ResponseEntity.status(HttpStatus.OK).body("인증 번호를 발송했습니다.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 번호를 발송을 실패했습니다.");
        }
    }

    // 문자 - 인증 번호 확인 api
    @PostMapping("/confirm-sms")
    public ResponseEntity<String> confirmSmsToFindEmail(@RequestBody GetConfirmSmsReq getConfirmSmsReq) {
        // redis에서 키 값에 해당하는 전화번호 통해 생성된 인증번호 불러옴
        String redisNumber = certifyService.getSmsCertification(getConfirmSmsReq.getPhoneNumber());

        // 인증 번호 유효 시간이 만료된 경우
        if (redisNumber == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 유효시간이 만료되었습니다.\n다시 진행해주세요.");

        // 유저가 입력한 인증번호와 redis 상의 인증번호 대조
        if (!getConfirmSmsReq.getCertifyNumber().equals(redisNumber))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");

        return ResponseEntity.status(HttpStatus.OK).body("인증되었습니다.");
    }


    // 인증 번호 이메일 발송 api
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmailToFindPasswd(@RequestBody @Valid PostSendEmailReq postSendEmailReq) {
        try {
            // 인증 번호 생성
            String verifyNumber = certifyService.createVerifyNumber();

            // 인증 번호 메일 발송
            certifyService.joinEmail(postSendEmailReq.getEmail(), verifyNumber);

            // 인증번호 redis에 3분 간 저장
            certifyService.createEmailCertification(postSendEmailReq.getEmail(), verifyNumber);
            return ResponseEntity.status(HttpStatus.OK).body("인증 번호를 발송했습니다.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 번호를 발송을 실패했습니다.");
        }
    }

    // 이메일 - 인증 번호 확인 api
    @PostMapping("confirm-email")
    public ResponseEntity<String> confirmEmailToFindPasswd(@RequestBody GetConfirmEmailReq getConfirmEmailReq) {
        // redis에서 키 값에 해당하는 전화번호 통해 생성된 인증번호 불러옴
        String redisNumber = certifyService.getEmailCertification(getConfirmEmailReq.getEmail());

        // 인증 번호 유효시간이 만료된 경우
        if (redisNumber == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 유효시간이 만료되었습니다.\n다시 진행해주세요.");

        // 유저가 입력한 인증번호와 redis 상의 인증번호 대조
        if (!getConfirmEmailReq.getCertifyNumber().equals(redisNumber))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");

        return ResponseEntity.status(HttpStatus.OK).body("인증되었습니다.");
    }
}
