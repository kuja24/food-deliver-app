package com.food.delivery.fooddelivery.models;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    @NotNull(message = "Address Line Type Cannot be null")
    private String addressLine;

    @NotNull(message = "City Type Cannot be null")
    private String city;

    @NotNull(message = "State Type Cannot be null")
    private String state;

    @NotNull(message = "Zip Type Cannot be null")
    private String zip;
}
