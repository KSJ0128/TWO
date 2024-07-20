package com.togetherwithocean.TWO.Badge.Controller;

import com.togetherwithocean.TWO.Badge.DTO.BadgeRes;
import com.togetherwithocean.TWO.Badge.Service.BadgeService;
import com.togetherwithocean.TWO.Member.DTO.PostFindMemberEmailReq;
import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Member.Repository.MemberRepository;
import com.togetherwithocean.TWO.MemberBadge.Repository.MemberBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/badge")
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;
    private final MemberRepository memberRepository;

    @GetMapping("/all")
    public ResponseEntity<List<BadgeRes>> findMemberEmail(Authentication principal) {
        Member member = memberRepository.findMemberByEmail(principal.getName());
        if (member == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.status(HttpStatus.OK).body(badgeService.findAllBagdes(member));
    }
}
