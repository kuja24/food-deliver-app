package com.food.delivery.fooddelivery.repository;

import com.food.delivery.fooddelivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    // Additional custom queries if needed
}