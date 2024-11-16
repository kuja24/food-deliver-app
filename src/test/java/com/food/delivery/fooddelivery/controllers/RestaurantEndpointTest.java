package com.food.delivery.fooddelivery.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.fooddelivery.TestSecurityConfig;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
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
        UserDetails userDetails = User.withUsername("testUser")
                .password("password")
                .authorities("CUSTOMER")  // Mock authority
                .build();

        // Create a mock authentication object with the user details
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Set the authentication in the SecurityContext
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);



    }

    @Test
    void getAllRestaurants_ShouldReturnRestaurantList() throws Exception {
        // Mock service to return a list of RestaurantDto
        List<RestaurantDto> restaurants = new ArrayList<>();
        restaurants.add(restaurantDto);
        when(restaurantService.getRestaurants(any(),any(),any(),any(),any(),any(),any())).thenReturn(restaurants);

        // Perform GET request and validate the response
        mockMvc.perform(get("/api/v1/restaurants")
                        .param("name","Test Restaurant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Restaurant"))
                .andExpect(jsonPath("$[0].cuisineType").value("Italian"));
    }

    @Test
    void addRestaurant_ShouldReturnRestaurantId() throws Exception {
        // Mock service to return a restaurant ID when creating a restaurant
        when(restaurantService.createRestaurant(restaurantRequest)).thenReturn(1L);

        // Perform POST request and validate the response
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
