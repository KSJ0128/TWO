package com.togetherwithocean.TWO.Recommend.Controller;

import com.togetherwithocean.TWO.Recommend.DTO.RecommendPlaceDTO;
import com.togetherwithocean.TWO.Recommend.Service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {
    private final RecommendService recommendService;
    @GetMapping("")
    public ResponseEntity<RecommendPlaceDTO> getRecommendPlace(Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        RecommendPlaceDTO recommendPlace = recommendService.getRecommendPlace();
        return ResponseEntity.status(HttpStatus.OK).body(recommendPlace);
    }

}
