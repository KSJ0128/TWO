package com.togetherwithocean.TWO.Stat;

import org.springframework.data.jpa.repository.JpaRepository;
import com.togetherwithocean.TWO.Recommend.Domain.Recommend;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    Recommend findRecommendByName(String name);
}
