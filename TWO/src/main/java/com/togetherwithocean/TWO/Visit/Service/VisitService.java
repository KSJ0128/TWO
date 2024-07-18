package com.togetherwithocean.TWO.Visit.Service;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Visit.DTO.MyPlaceDTO;
import com.togetherwithocean.TWO.Visit.Repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;

    public List<MyPlaceDTO> getMyPlace(Member member) {
        return visitRepository.findDistinctNameByMember(member);
    }
}
