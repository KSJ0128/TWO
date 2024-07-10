package com.togetherwithocean.TWO.Plogging.Controller;

import com.togetherwithocean.TWO.PlogCalendar.Domain.PlogCalendar;
import com.togetherwithocean.TWO.Plogging.DTO.PostPlogReq;
import com.togetherwithocean.TWO.Plogging.Service.PloggingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/plogging")
public class PloggingController {
    private final PloggingService ploggingService;

    public PloggingController(PloggingService ploggingService) {
        this.ploggingService = ploggingService;
    }

    // 줍깅 - 장소 입력 API
    @GetMapping("/location")
    public ResponseEntity<String> getPloggingLocation (@RequestParam String address) {
        if (address == null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    // 줍깅 API
    @PostMapping("/plog")
    public ResponseEntity<PlogCalendar> doPlogging (@RequestBody PostPlogReq postPlogReq) {
       PlogCalendar plog =  ploggingService.savePlog(postPlogReq);
       return ResponseEntity.status(HttpStatus.OK).body(plog);
    }
}
