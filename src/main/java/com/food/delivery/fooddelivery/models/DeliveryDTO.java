package com.food.delivery.fooddelivery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.food.delivery.fooddelivery.entity.DeliveryStatus;


import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {

    private Long deliveryId;  // Add this field for deliveryId
    private Long orderId;  // Add the orderId field if required
    private DeliveryStatus status;
    private Date pickedUpAt;
    private Date deliveredAt;
    private Long deliveryPartnerId;  // Assuming delivery partner is represented by userId
    private Long addressId;  // Assuming the addressId is provided

}