package com.food.delivery.fooddelivery.models;

import com.food.delivery.fooddelivery.entity.Address;
import com.food.delivery.fooddelivery.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
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
