package com.food.delivery.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String name;
    private String email;
    private String passwordHash;
    private String phone;
    private String role;
    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)  // This specifies the foreign key column
    private Address address;

    private Date createdAt;
}
