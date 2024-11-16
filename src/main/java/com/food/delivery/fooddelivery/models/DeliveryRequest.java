package com.food.delivery.fooddelivery.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryRequest {

    @Valid
    @NotNull
    private DeliveryDto deliveryDto;

    @NotNull(message = "Order Id is mandatory")
    private Long orderId;

    @NotNull(message = "Owner Id of the restaurant owner is mandatory")
    private Long ownerId;
}
