package com.togetherwithocean.TWO.User.Repository;
import com.togetherwithocean.TWO.User.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByRealName(String realName);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

}
