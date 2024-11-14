package com.food.delivery.fooddelivery.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuRequest {

    @Valid
    @NotNull
    private MenuDto menuDto;

    @NotNull(message = "Id of the restaurant is mandatory")
    private Long restaurantId;
}
