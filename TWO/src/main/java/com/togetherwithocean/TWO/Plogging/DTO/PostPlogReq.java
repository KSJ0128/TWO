package com.togetherwithocean.TWO.Plogging.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PostPlogReq {
    Long memberNumber;
    LocalDate plogDate;
    Long plogStep;
    Long plogTrash;
    String plogLoc;

    public PostPlogReq(Long memberNumber, LocalDate plogDate, Long plogStep, Long plogTrash, String plogLoc) {
        this.memberNumber = memberNumber;
        this.plogDate = plogDate;
        this.plogStep = plogStep;
        this.plogTrash = plogTrash;
        this.plogLoc = plogLoc;
    }
}
