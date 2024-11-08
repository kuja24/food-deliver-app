package com.food.delivery.fooddelivery.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantRequest {

    @Valid
    @NotNull
    private RestaurantDto restaurant;

    @NotNull(message = "Owner Id of the restaurant owner is mandatory")
    private Long ownerId;

}
