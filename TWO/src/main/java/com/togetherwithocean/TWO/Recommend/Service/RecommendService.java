package com.togetherwithocean.TWO.Recommend.Service;

import com.togetherwithocean.TWO.Member.Domain.Member;
import com.togetherwithocean.TWO.Recommend.DTO.RecommendPlaceDTO;
import com.togetherwithocean.TWO.Recommend.Domain.Recommend;
import com.togetherwithocean.TWO.Recommend.Repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRepository;

    String findNowDirection() {
        Long recCount = recommendRepository.getRecommendedCount(); // 지금까지 추천한 장소의 개수

        // 추천한 장소 개수를 기반으로 이번에 추천할 장소의 위치 결정
        String direction = "";
        if (recCount % 4 == 0)
            direction = "동해";
        else if (recCount % 4 == 1)
            direction = "서해";
        else if (recCount % 4 == 2)
            direction = "남해";
        else if (recCount % 4 == 3)
            direction = "제주도";

        return direction;
    }

    public RecommendPlaceDTO getRecommendPlace() {
        Recommend recommendLoc = recommendRepository.getRecommendByDir(findNowDirection());

        return RecommendPlaceDTO.builder()
                .longtitude(recommendLoc.getLongitude())
                .latitude(recommendLoc.getLatitude())
                .direction(recommendLoc.getDirection())
                .name(recommendLoc.getName())
                .build();
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void initDailyAchieve() {
        // 한 주간 추천한 장소 플래그 true로 변경
        String nowDirection = findNowDirection();
        Recommend nowRecommend = recommendRepository.getRecommendByDir(nowDirection);
        nowRecommend.setRecs(true);
        recommendRepository.save(nowRecommend);

        // 해당 direction의 장소들이 이미 모두 추천된 경우 (새로 추천할 장소가 없는 경우)
        // -> 해당 direction 장소들의 추천 여부를 다시 전부 false로 변경
        if (recommendRepository.getRecommendByDir(findNowDirection()) == null) {
            recommendRepository.updateRecsFalseByDirection(nowDirection);
        }
    }
}
