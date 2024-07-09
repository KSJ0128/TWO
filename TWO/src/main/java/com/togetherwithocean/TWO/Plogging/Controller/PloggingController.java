package com.togetherwithocean.TWO.Plogging.Controller;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Plogging.Service.PloggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/plogging")
@RequiredArgsConstructor
public class PloggingController {
    private final PloggingService ploggingService;

    @GetMapping("/walk")
    ResponseEntity<Member> saveStep (@RequestParam Long step, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Member member = ploggingService.saveStep(principal.getName(), step);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    // 줍깅 - 장소 입력 API
    @GetMapping("/location")
    ResponseEntity<String> getPloggingLocation (@RequestParam String address) {
        if (address == null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(address);
    }
}
