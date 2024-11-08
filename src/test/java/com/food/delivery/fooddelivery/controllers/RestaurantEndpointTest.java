package com.food.delivery.fooddelivery.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.fooddelivery.models.AddressDto;
import com.food.delivery.fooddelivery.models.RestaurantDto;
import com.food.delivery.fooddelivery.models.RestaurantRequest;
import com.food.delivery.fooddelivery.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    private RestaurantRequest restaurantRequest;
    private RestaurantDto restaurantDto;

    @BeforeEach
    void setUp() {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressLine("123 st");
        addressDto.setCity("blr");
        addressDto.setState("krtk");
        addressDto.setZip("233243");
        // Mock RestaurantDto for GET
        restaurantDto = new RestaurantDto();
        restaurantDto.setName("Test Restaurant");
        restaurantDto.setCuisineType("Italian");
        restaurantDto.setHoursOfOperation(new Date());
        restaurantDto.setAddress(addressDto);

        // Mock RestaurantRequest for POST
        restaurantRequest = new RestaurantRequest();
        restaurantRequest.setOwnerId(1L);
        restaurantRequest.setRestaurant(restaurantDto);

    }

    @Test
    void getAllRestaurants_ShouldReturnRestaurantList() throws Exception {
        // Mock service to return a list of RestaurantDto
        List<RestaurantDto> restaurants = Arrays.asList(restaurantDto);
        when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        // Perform GET request and validate the response
        mockMvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Restaurant"))
                .andExpect(jsonPath("$[0].cuisineType").value("Italian"));
    }

    @Test
    void addRestaurant_ShouldReturnRestaurantId() throws Exception {
        // Mock service to return a restaurant ID when creating a restaurant
        when(restaurantService.createRestaurant(restaurantRequest)).thenReturn(1L);

        // Perform POST request and validate the response
        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
