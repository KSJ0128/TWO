package com.togetherwithocean.TWO.Badge.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.togetherwithocean.TWO.Badge.Domain.Badge;
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Badge findBadgeByBadgeNumber(Long badgeNumber);
}
