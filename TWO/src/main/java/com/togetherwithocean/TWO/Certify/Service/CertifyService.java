package com.togetherwithocean.TWO.Certify.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
public class CertifyService {
    private final String PREFIX_SMS = "SMS:";
    private final String PREFIX_EMAIL = "EMAIL:";
    private final int LIMIT_TIME = 3 * 60;
    private JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;
    private String mailUser;

    public CertifyService(JavaMailSender mailSender, StringRedisTemplate redisTemplate, @Value("${spring.mail.username}")
    String mailUser) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
        this.mailUser = mailUser;
    }

    // redis에 전화번호 및 인증 번호 키-값으로 저장
    public void createSmsCertification(String phone, String certificationNumber) {
        redisTemplate.opsForValue()
                .set(PREFIX_SMS + phone, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    // redis에 이메일 및 인증 번호 키-값으로 저장
    public void createEmailCertification(String email, String certificationNumber) {
        redisTemplate.opsForValue()
                .set(PREFIX_EMAIL + email, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    // redis에 전화번호 키 값으로 인증 번호 불러오기
    public String getSmsCertification(String phone) {
        return redisTemplate.opsForValue().get(PREFIX_SMS + phone);
    }

    // redis에 이메일 키 값으로 인증 번호 불러오기
    public String getEmailCertification(String email) {
        return redisTemplate.opsForValue().get(PREFIX_EMAIL + email);
    }

//    public void removeSmsCertification(String phone) {
//        redisTemplate.delete(PREFIX + phone);
//    }
//
//    public boolean hasKey(String phone) {
//        return redisTemplate.hasKey(PREFIX + phone);
//    }

    // 인증 번호 생성
    @Transactional
    public String createVerifyNumber() {
        int randomNumber = (int)(Math.random() * 10000) + (int)(Math.random() * 100);

        return String.valueOf(randomNumber);
    }

    @Transactional
    public void mailSend(String setFrom, String toEmail, String title, String content) throws MessagingException {

        // JavaMailSender 객체 사용해 MimeMessage 객체 생성
        MimeMessage message = mailSender.createMimeMessage();
        // 이메일 메시지 관련 설정
        // true를 전달하여 multipart 형식의 메시지를 지원 및 "utf-8"을 전달해 문자 인코딩 설정
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        // 이메일의 발신자 주소 설정
        helper.setFrom(setFrom);
        // 이메일의 수신자 주소 설정
        helper.setTo(toEmail);
        // 이메일의 제목을 설정
        helper.setSubject(title);
        // 이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
        helper.setText(content, true);
        mailSender.send(message);
    }

    // 이메일 전송
    @Transactional
    public void joinEmail(String email, String verifyNumber) throws MessagingException {
        String setFrom = mailUser;
        String tomail = email;
        String title = "[TWO] 회원가입 본인 확인 인증번호입니다."; // 이메일 제목
        String content =
                        "회원가입 본인 확인 인증 번호는 " + verifyNumber + "입니다." +
                        "<br>" +
                        "인증번호를 입력해주세요."; // 이메일 내용 삽입
        mailSend(setFrom, tomail, title, content);
    }
}
