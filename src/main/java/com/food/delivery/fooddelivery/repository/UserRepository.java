package com.food.delivery.fooddelivery.repository;

import com.food.delivery.fooddelivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserId(Long userId);
    public User findByEmail(String email);
}
