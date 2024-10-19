package com.food.delivery.fooddelivery.repository;

import com.food.delivery.fooddelivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // You can define custom query methods here if needed
}
