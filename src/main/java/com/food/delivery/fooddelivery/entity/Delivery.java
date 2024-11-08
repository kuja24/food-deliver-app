package com.food.delivery.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Entity(name = "DELIVERY")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    /*@ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderDetails orderDetails; // Assuming you have an OrderDetails entity*/
    //@Column(name = "order_id", nullable = true)  // Set nullable to true
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "delivery_partner_id", nullable = false)
    private User deliveryPartner; // Assuming you have a Users entity

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status; // Enum for the status (assigned, picked_up, en_route, delivered)

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "picked_up_at")
    private Timestamp pickedUpAt;

    @Column(name = "delivered_at")
    private Timestamp deliveredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address; // Foreign key to Address for the delivery location
}