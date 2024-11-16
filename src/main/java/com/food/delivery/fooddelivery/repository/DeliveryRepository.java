package com.food.delivery.fooddelivery.repository;

import com.food.delivery.fooddelivery.entity.Delivery;
import com.food.delivery.fooddelivery.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>, JpaSpecificationExecutor<Delivery> {
}
