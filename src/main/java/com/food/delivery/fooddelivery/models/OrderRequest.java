package com.food.delivery.fooddelivery.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

    @Valid
    @NotNull
    private OrdersDto orders;

    @NotNull(message = "Menu Id of the restaurant is mandatory")
    private Long menu_id;

}
