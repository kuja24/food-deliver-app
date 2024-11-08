package com.food.delivery.fooddelivery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.food.delivery.fooddelivery.entity.DeliveryStatus;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponseDTO {

    private Long deliveryId;
    private Long orderId;
    private Long deliveryPartnerId;
    private DeliveryStatus status;
    private Timestamp createdAt;
    private Timestamp pickedUpAt;
    private Timestamp deliveredAt;
    private Long addressId;  // Add the addressId field
}