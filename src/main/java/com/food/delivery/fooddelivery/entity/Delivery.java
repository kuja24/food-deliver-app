package com.food.delivery.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long delivery_id;

    @OneToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Orders orders;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private User owner;

    private String status;

    private Timestamp picked_up_at;

    private Timestamp created_at;
}
