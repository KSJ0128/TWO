package com.togetherwithocean.TWO.Visit.Controller;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.Visit.DTO.MyPlaceDTO;
import com.togetherwithocean.TWO.Visit.Repository.VisitRepository;
import com.togetherwithocean.TWO.Visit.Service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("visit")
@RequiredArgsConstructor
public class VisitController {
    private final MemberRepository memberRepository;

    private final VisitService visitService;

    @GetMapping("/myplace")
    public ResponseEntity<List<MyPlaceDTO>> getMemberVisitList(Authentication principal) {
        if (principal == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Member member = memberRepository.findMemberByEmail(principal.getName());
        List<MyPlaceDTO> myPlace = visitService.getMyPlace(member);
        return ResponseEntity.status(HttpStatus.OK).body(myPlace);
    }
}
