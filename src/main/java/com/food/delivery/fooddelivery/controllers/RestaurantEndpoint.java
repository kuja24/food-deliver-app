package com.food.delivery.fooddelivery.controllers;

import com.food.delivery.fooddelivery.models.RestaurantDto;
import com.food.delivery.fooddelivery.models.RestaurantRequest;
import com.food.delivery.fooddelivery.service.RestaurantService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@SecurityRequirement(name = "bearerAuth")
public class RestaurantEndpoint {

    private final RestaurantService restaurantService;

    public RestaurantEndpoint(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER','RESTAURANT_OWNER','ADMIN','DELIVERY_PARTNER')")
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('RESTAURANT_OWNER','ADMIN')")
    public ResponseEntity<Long> addRestaurant(@Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok().body(restaurantService.createRestaurant(request));
    }
}
