package com.food.delivery.fooddelivery.models;

import com.food.delivery.fooddelivery.entity.Address;
import com.food.delivery.fooddelivery.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RestaurantDto {
    private String name;
    private Address address;
    private String cuisineType;
    private Date hoursOfOperation;
}
