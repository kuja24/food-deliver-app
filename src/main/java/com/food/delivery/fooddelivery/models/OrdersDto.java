package com.food.delivery.fooddelivery.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;

    @NotNull(message = "Price of the order cannot be empty")
    private Integer price;
}
