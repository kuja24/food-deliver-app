package com.food.delivery.fooddelivery.controllers;

import com.food.delivery.fooddelivery.entity.Restaurant;
import com.food.delivery.fooddelivery.models.RestaurantDto;
import com.food.delivery.fooddelivery.models.RestaurantRequest;
import com.food.delivery.fooddelivery.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/v1/")
public class RestaurantEndpoint {

    private final RestaurantService restaurantService;

    public RestaurantEndpoint(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @PostMapping("/restaurants")
    public ResponseEntity<Long> addRestaurant(@Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok().body(restaurantService.createRestaurant(request));
    }
}
