package com.togetherwithocean.TWO.Plogging.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/plogging")
public class PloggingController {

    // 줍깅 - 장소 입력 API
    @GetMapping("/location")
    ResponseEntity<String> getPloggingLocation (@RequestParam String address) {
        if (address == null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(address);
    }
}
