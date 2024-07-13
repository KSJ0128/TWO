package com.togetherwithocean.TWO.Location.Repository;

import com.togetherwithocean.TWO.Location.Domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findLocationByName(String name);
}
