package com.togetherwithocean.TWO.Plog.Controller;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Plog.DTO.DailyReq;
import com.togetherwithocean.TWO.Plog.DTO.PlogReq;
import com.togetherwithocean.TWO.Plog.Domain.Plog;
import com.togetherwithocean.TWO.Plog.Service.PlogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/plog")
public class PlogController {

    private final PlogService plogService;

    @PostMapping("/new")
    ResponseEntity<Plog> savePlog(@RequestBody PlogReq plogReq, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Plog plog = plogService.savePlog(principal.getName(), plogReq);
        return ResponseEntity.status(HttpStatus.OK).body(plog);
    }

    @GetMapping("/walk")
    ResponseEntity<Member> saveStep(@RequestParam Long step, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Member member = plogService.saveStep(principal.getName(), step);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    @PostMapping("/daily")
    ResponseEntity<List<Plog>> getPlogList(@RequestBody DailyReq dateReq, Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<Plog> plogs = plogService.getPlogs(principal.getName(), dateReq);
        return ResponseEntity.status(HttpStatus.OK).body(plogs);
    }
}
