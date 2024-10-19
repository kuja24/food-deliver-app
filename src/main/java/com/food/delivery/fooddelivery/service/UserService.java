package com.food.delivery.fooddelivery.service;

import com.food.delivery.fooddelivery.entity.User;
import com.food.delivery.fooddelivery.exception.FoodDeliveryException;
import com.food.delivery.fooddelivery.models.AddressDto;
import com.food.delivery.fooddelivery.models.UserDto;
import com.food.delivery.fooddelivery.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUserId(Long userId) {
        User user;
        try {
            user = userRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new FoodDeliveryException("User not found with id: " + userId, HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public UserDto getUserDtoFromUser(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .address(AddressDto.builder()
                        .addressLine(user.getAddress().getAddressLine())
                        .state(user.getAddress().getState())
                        .city(user.getAddress().getCity())
                        .zip(user.getAddress().getZipCode())
                        .build())
                .build();
    }
}
