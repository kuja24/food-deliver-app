package com.food.delivery.fooddelivery.controllers;

import com.food.delivery.fooddelivery.models.CreateUserRequest;
import com.food.delivery.fooddelivery.models.UserDto;
import com.food.delivery.fooddelivery.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/users")
public class UsersEndpoint {

    private final UserService userService;


    public UsersEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok().body(userService.createUser(request));
    }
}
