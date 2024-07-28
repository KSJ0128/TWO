package com.togetherwithocean.TWO.Recommend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.togetherwithocean.TWO.Recommend.Domain.Recommend;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    Recommend findRecommendByName(String name);
    @Query("SELECT COUNT(*) FROM Recommend WHERE recs=true")
    Long getRecommendedCount();
    @Query("SELECT r FROM Recommend r WHERE r.direction = :direction AND r.recs = false ORDER BY r.recNumber LIMIT 1")
    Recommend getRecommendByDir(@Param("direction") String direction);


    @Query("UPDATE Recommend r SET r.recs = false WHERE r.direction = :direction")
    void updateRecsByDirection(@Param("direction") String direction);

}
