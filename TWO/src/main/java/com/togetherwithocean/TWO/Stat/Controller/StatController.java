package com.togetherwithocean.TWO.Stat.Controller;

import com.togetherwithocean.TWO.Stat.DTO.GetMonthlyCalendarReq;
import com.togetherwithocean.TWO.Stat.DTO.GetMonthlyStatRes;
import com.togetherwithocean.TWO.Stat.DTO.PatchStatWalkReq;
import com.togetherwithocean.TWO.Stat.DTO.PostStatSaveReq;
import com.togetherwithocean.TWO.Stat.Domain.Stat;
import com.togetherwithocean.TWO.Stat.Service.StatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("stat")
public class StatController {
    private final StatService statService;

    // 줍깅 api
    @PostMapping("/plog")
    ResponseEntity<Stat> savePlog(@RequestBody PostStatSaveReq postStatSaveReq, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Stat plog = statService.savePlog(principal.getName(), postStatSaveReq);
        return ResponseEntity.status(HttpStatus.OK).body(plog);
    }

    // 걸음수 갱신 api
    @GetMapping("/walk")
    ResponseEntity<Stat> saveStep(@RequestBody PatchStatWalkReq patchStatWalkReq, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Stat stat = statService.saveStep(patchStatWalkReq, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(stat);
    }

    // 일자별 캘린더 조회
    @PostMapping("/daily")
    ResponseEntity<Stat> getPlogList(@RequestParam LocalDate date, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Stat dailyStat = statService.getPlogs(principal.getName(), date);
        return ResponseEntity.status(HttpStatus.OK).body(dailyStat);
    }

    // 월별 캘린더 및 줍깅 수, 스코어 조회
    @GetMapping("/monthly")
    public ResponseEntity<GetMonthlyStatRes> getMonthlyStat(@RequestBody GetMonthlyCalendarReq getMonthlyCalendarReq, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        GetMonthlyStatRes monthlyStat = statService.getMonthlyStat(getMonthlyCalendarReq, principal.getName());
        if (monthlyStat == null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(monthlyStat);
    }
}
