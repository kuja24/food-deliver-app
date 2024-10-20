package com.food.delivery.fooddelivery.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {

    @NotNull(message = "Email cant be null")
    private String email;

    @NotNull(message = "Password cant be null")
    private String password;
}
