package com.food.delivery.fooddelivery.controllers;

import com.food.delivery.fooddelivery.exception.ErrorDetails;
import com.food.delivery.fooddelivery.models.CreateUserRequest;
import com.food.delivery.fooddelivery.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersEndpoint {

    private final UserService userService;


    public UsersEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "This endpoint allows new users to register by providing their name, password, role & other necessary details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully registered"),
                    @ApiResponse(responseCode = "400", description = "Invalid registration details",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(userService.createUser(request));
    }
}
