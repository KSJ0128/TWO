package com.togetherwithocean.TWO.Statistics.Controller;

import com.togetherwithocean.TWO.Statistics.DTO.GetMonthlyStatisticsReq;
import com.togetherwithocean.TWO.Statistics.DTO.GetMonthlyStatisticsRes;
import com.togetherwithocean.TWO.Statistics.Service.StatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/monthly")
    public ResponseEntity<List<GetMonthlyStatisticsRes>> getMonthlyStatistics(@RequestBody GetMonthlyStatisticsReq getMonthlyStatisticsReq, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        List<GetMonthlyStatisticsRes> monthlyInfo = statisticsService.getMonthlyInfo(getMonthlyStatisticsReq, principal);
        if (monthlyInfo == null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(monthlyInfo);
    }

}
