package com.togetherwithocean.TWO.PlogCalendar.Controller;

import com.togetherwithocean.TWO.PlogCalendar.DTO.GetMonthlyReq;
import com.togetherwithocean.TWO.PlogCalendar.DTO.GetMonthlyRes;
import com.togetherwithocean.TWO.PlogCalendar.Service.PlogCalendarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/calendar")
public class PlogCalendarController {

    private final PlogCalendarService plogCalendarService;
    public PlogCalendarController(PlogCalendarService calendarService) {
        this.plogCalendarService = calendarService;
    }
    @GetMapping("/monthly")
    public ResponseEntity<List<GetMonthlyRes>> getMonthlyCalendar(@RequestBody GetMonthlyReq getMonthlyReq, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        List<GetMonthlyRes> monthlyInfo = plogCalendarService.getMonthlyInfo(getMonthlyReq, principal);
        if (monthlyInfo == null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(monthlyInfo);
    }
}
