package com.food.delivery.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "RESTAURANTS")
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long restaurantId;

    private String name;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)  // This specifies the foreign key column
    private Address address;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private User owner;

    private String cuisineType;

    private Date hoursOfOperation;

    private Date createdAt;
}
