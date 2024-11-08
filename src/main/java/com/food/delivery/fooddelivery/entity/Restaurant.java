package com.food.delivery.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "RESTAURANTS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
