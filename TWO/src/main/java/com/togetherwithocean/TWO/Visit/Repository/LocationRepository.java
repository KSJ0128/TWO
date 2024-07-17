package com.togetherwithocean.TWO.Visit.Repository;

import com.togetherwithocean.TWO.Visit.Domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Visit, Long> {
    Visit findLocationByName(String name);
}
