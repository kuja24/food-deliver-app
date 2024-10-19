package com.food.delivery.fooddelivery.repository;

import com.food.delivery.fooddelivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
