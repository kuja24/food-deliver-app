package com.food.delivery.fooddelivery.service;

import com.food.delivery.fooddelivery.entity.Address;
import com.food.delivery.fooddelivery.entity.Restaurant;
import com.food.delivery.fooddelivery.entity.User;
import com.food.delivery.fooddelivery.exception.FoodDeliveryException;
import com.food.delivery.fooddelivery.models.AddressDto;
import com.food.delivery.fooddelivery.models.RestaurantDto;
import com.food.delivery.fooddelivery.models.RestaurantRequest;
import com.food.delivery.fooddelivery.models.UserDto;
import com.food.delivery.fooddelivery.repository.RestaurantRepository;
import com.food.delivery.fooddelivery.util.RestaurantSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService, AddressService addressService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.addressService = addressService;
    }

    public List<RestaurantDto> getAllRestaurants() {
        List<RestaurantDto> restaurantDtos = new ArrayList<>();
        List<Restaurant> restaurants = (List<Restaurant>) restaurantRepository.findAll();
        restaurants.forEach(restaurant -> {
            restaurantDtos.add(RestaurantDto.builder()
                    .name(restaurant.getName())
                    .address(AddressDto.builder()
                            .addressLine(restaurant.getAddress().getAddressLine())
                            .state(restaurant.getAddress().getState())
                            .city(restaurant.getAddress().getCity())
                            .zip(restaurant.getAddress().getZipCode())
                            .build())
                    .hoursOfOperation(restaurant.getHoursOfOperation())
                    .cuisineType(restaurant.getCuisineType())
                    .build());
        });
        return restaurantDtos;
    }

    @Transactional
    public Long createRestaurant(RestaurantRequest request) {
        User user;
        try {
            user = userService.findUserByUserId(request.getOwnerId());
        } catch (Exception e) {
            throw new FoodDeliveryException("Restaurant Owner not found. Please enter valid owner id", HttpStatus.BAD_REQUEST);
        }
        //persists address
        Address addr = addressService.saveAddress(request.getRestaurant().getAddress());
        //persist restaurant
        Restaurant res = restaurantRepository.save(buildRestaurantEntity(user, addr, request.getRestaurant()));
        return res.getRestaurantId();

    }

    @Transactional
    public RestaurantDto updateRestaurant(Long restaurantId, RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new FoodDeliveryException("Restaurant not found with id: " + restaurantId, HttpStatus.BAD_REQUEST));

        Address addr = addressService.saveAddress(restaurantDto.getAddress());
        // Update the restaurant entity with new details
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(addr);
        restaurant.setCuisineType(restaurantDto.getCuisineType());
        restaurant.setHoursOfOperation(restaurantDto.getHoursOfOperation());

        // Save the updated entity back to the database
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        // Convert the updated entity back to DTO and return
        return convertEntityToDto(updatedRestaurant);
    }

    public List<RestaurantDto> getRestaurants(String name, String addressLine, String city, String state, String zip, String cuisineType, String hoursOfOperation) {
        // Build the specification dynamically
        Specification<Restaurant> spec = RestaurantSpecification.filterByCriteria(name, addressLine, city, state, zip, cuisineType, hoursOfOperation);

        // Fetch restaurants using the specification
        List<Restaurant> restaurants = restaurantRepository.findAll(spec);

        // Convert to DTOs
        return restaurants.stream().map(this::convertEntityToDto).toList();
    }

    public boolean deleteRestaurantById(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private RestaurantDto convertEntityToDto(Restaurant restaurant) {
        AddressDto addressDto = AddressDto.builder()
                .addressLine(restaurant.getAddress().getAddressLine())
                .state(restaurant.getAddress().getState())
                .city(restaurant.getAddress().getCity())
                .zip(restaurant.getAddress().getZipCode())
                .build();

        return RestaurantDto.builder()
                .name(restaurant.getName())
                .cuisineType(restaurant.getCuisineType())
                .hoursOfOperation(restaurant.getHoursOfOperation())
                .address(addressDto)
                .build();
    }

    private Restaurant buildRestaurantEntity(User user, Address addr, RestaurantDto restaurantDto) {
        return Restaurant.builder()
                .address(addr)
                .owner(user)
                .name(restaurantDto.getName())
                .cuisineType(restaurantDto.getCuisineType())
                .hoursOfOperation(restaurantDto.getHoursOfOperation())
                .createdAt(new Date())
                .build();
    }
}
