package com.togetherwithocean.TWO.Jwt;

import com.togetherwithocean.TWO.Jwt.JwtAuthenticationFilter;
import com.togetherwithocean.TWO.Jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        // JWT를 사용하기 때문에 세션을 사용하지 않음
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/member/sign-in", "/member/join",
                        "/member/check-email", "/member/check-nick",
                        "member/find-email", "member/find-pw",
                        "certify/send-sms", "certify/send-email",
                        "certify/confirm-sms", "certify/confirm-email",
                        "/oauth/kakao/login", "/oauth/kakao/callback"
                ).permitAll()
                .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
