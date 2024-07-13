package com.togetherwithocean.TWO.Location.Repository;

import com.togetherwithocean.TWO.Location.Domain.Location;

public interface LocationRepository {
    Location findLocationByName(String name);
}
