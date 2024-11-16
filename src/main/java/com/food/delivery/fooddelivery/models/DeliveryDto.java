package com.food.delivery.fooddelivery.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    @NotNull(message = "Status cannot be null")
    private String status;

    @NotNull(message = "Picked up time is mandatory")
    private Timestamp picked_up_at;

    @NotNull(message = "Created time is mandatory")
    private Timestamp created_at;
}
