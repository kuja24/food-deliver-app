package com.food.delivery.fooddelivery.models;

import com.food.delivery.fooddelivery.entity.Restaurant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    @NotEmpty(message = "Item name cannot be null")
    @Size(min = 2, max = 32, message = "Item name must be between 2 and 32 characters long")
    private String item_name;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Price cannot be empty")
    private float price;

    @NotNull(message = "Availability status cannot be null")
    private Boolean is_available;
}
