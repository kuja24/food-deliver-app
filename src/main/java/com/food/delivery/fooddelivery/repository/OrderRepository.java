package com.food.delivery.fooddelivery.repository;

import com.food.delivery.fooddelivery.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {
    // You can define custom query methods here if needed
    public Orders findByOrderId(Long orderId);
}
