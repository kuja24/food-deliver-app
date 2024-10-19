package com.food.delivery.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "ADDRESS")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "address_id")
    private Long addressId;
    private String addressLine;
    private String city;
    private String state;
    private String zipCode;
}
