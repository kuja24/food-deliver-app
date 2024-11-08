package com.food.delivery.fooddelivery.repository;

import com.food.delivery.fooddelivery.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
