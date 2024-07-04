package com.togetherwithocean.TWO.Verify;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class SmsDao {
    private final String PREFIX = "sms:";
    private final int LIMIT_TIME = 3 * 60;

    private final StringRedisTemplate redisTemplate;

    public void createSmsCertification(String phone, String certificationNumber) {
        System.out.println("여기까지는 오니?");
        redisTemplate.opsForValue()
                .set(PREFIX + phone, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
        System.out.println("여기까지 오니?");
    }

    public String getSmsCertification(String phone) {
        return redisTemplate.opsForValue().get(PREFIX + phone);
    }

    public void removeSmsCertification(String phone) {
        redisTemplate.delete(PREFIX + phone);
    }

    public boolean hasKey(String phone) {
        return redisTemplate.hasKey(PREFIX + phone);
    }
}
