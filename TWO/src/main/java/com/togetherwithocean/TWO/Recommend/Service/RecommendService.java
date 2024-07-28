package com.togetherwithocean.TWO.Recommend.Service;

import com.togetherwithocean.TWO.Recommend.DTO.RecommendPlaceDTO;
import com.togetherwithocean.TWO.Recommend.Domain.Recommend;
import com.togetherwithocean.TWO.Recommend.Repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRepository;
    public RecommendPlaceDTO getRecommendPlace() {
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

        Recommend recommendLoc = recommendRepository.getRecommendByDir(direction);

//        // 해당 위치의 장소들이 이미 모두 추천된 경우 -> 추천 여부를 다시 전부 false로 변경 후 다시 선택
//        if (recommendLoc == null) {
//            recommendRepository.updateRecsByDirection(direction);
//            recommendLoc = recommendRepository.getRecommendByDir(direction);
//        }
//        이건 주기적으로 true로 바꿔줄때 확인하고 갱신하자!

        return RecommendPlaceDTO.builder()
                .longtitude(recommendLoc.getLongitude())
                .latitude(recommendLoc.getLatitude())
                .direction(recommendLoc.getDirection())
                .name(recommendLoc.getName())
                .build();
    }
}
