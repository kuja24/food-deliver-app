package com.food.delivery.fooddelivery.models;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String name;
    private String email;
    private List<Address> addresses;
}
