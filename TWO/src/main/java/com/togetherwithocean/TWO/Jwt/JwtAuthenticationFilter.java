package com.togetherwithocean.TWO.Jwt;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

            // 1. Request Header에서 JWT 토큰 추출
            String accessToken = jwtProvider.resolveAccessToken((HttpServletRequest) request);
            String refreshToken = jwtProvider.resolveRefreshToken((HttpServletRequest) request);
            System.out.println("액세스 토큰: " + accessToken);
            System.out.println("리프레쉬 토큰: " + refreshToken);

            // 2. validateToken으로 토큰 유효성 검사
            if (accessToken != null && jwtProvider.validateAccessToken(accessToken)) {
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else if (refreshToken != null && jwtProvider.refreshTokenValidation(refreshToken, jwtProvider.parseClaims(accessToken).getSubject())) {
                System.out.println("액세스 토큰 만료");
                String email = jwtProvider.parseClaims(accessToken).getSubject();
                String newAccessToken = jwtProvider.createAccessToken(email);
                String newRefreshToken = jwtProvider.createRefreshToken(email);
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setHeader("AccessToken", newAccessToken);
                httpResponse.setHeader("RefreshToken", newRefreshToken);
                SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(accessToken));
                System.out.println("액세스 토큰 재발급");
                System.out.println(newAccessToken);
                System.out.println(newRefreshToken);
            }
            else
                System.out.println("유효하지 않은 토큰");

            chain.doFilter(request, response); // 다음 필터로 넘어가거나, 요청 처리 진행
    }
}
