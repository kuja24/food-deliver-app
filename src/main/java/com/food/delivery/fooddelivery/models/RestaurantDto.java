package com.food.delivery.fooddelivery.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String name;

    @NotNull(message = "Address Type Cannot be null")
    @Valid
    private AddressDto address;

    @NotNull(message = "Cuisine Type Cannot be null")
    private String cuisineType;

    @NotNull(message = "Hours of operation Type Cannot be null")
    private Date hoursOfOperation;
}
