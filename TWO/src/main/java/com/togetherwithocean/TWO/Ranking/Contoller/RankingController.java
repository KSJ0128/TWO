package com.togetherwithocean.TWO.Ranking.Contoller;

import com.togetherwithocean.TWO.Ranking.DTO.RankingDTO;
import com.togetherwithocean.TWO.Ranking.Service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/search")
    public ResponseEntity<List<RankingDTO>> searchTopTen(Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<RankingDTO> topTen = rankingService.getRanking();
        return ResponseEntity.status(HttpStatus.OK).body(topTen);
    }
}