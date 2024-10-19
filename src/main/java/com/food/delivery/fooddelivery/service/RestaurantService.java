package com.food.delivery.fooddelivery.service;

import com.food.delivery.fooddelivery.entity.Restaurant;
import com.food.delivery.fooddelivery.models.RestaurantDto;
import com.food.delivery.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantDto> getAllRestaurants() {
        List<RestaurantDto> restaurantDtos = new ArrayList<>();
        List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll();
        restaurants.forEach(restaurant -> {
            restaurantDtos.add(RestaurantDto.builder()
                    .name(restaurant.getName())
                    .address(restaurant.getAddress())
                    .hoursOfOperation(restaurant.getHoursOfOperation())
                    .cuisineType(restaurant.getCuisineType())
                    .build());
        });
        return restaurantDtos;
    }
}
