package com.food.delivery.fooddelivery.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.fooddelivery.TestSecurityConfig;
import com.food.delivery.fooddelivery.models.*;
import com.food.delivery.fooddelivery.service.OrderService;
import com.food.delivery.fooddelivery.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
class OrdersEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    private RestaurantRequest restaurantRequest;
    private RestaurantDto restaurantDto;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderRequest orderRequest;
    private OrdersDto ordersDto;

    @BeforeEach
    void setUp() {
        // Mock RestaurantDto for GET
        ordersDto = new OrdersDto();
        ordersDto.setQuantity(5);
        ordersDto.setPrice(50);

        // Mock RestaurantRequest for POST
        orderRequest = new OrderRequest();
        orderRequest.setOrders(ordersDto);
        orderRequest.setMenu_id(1L);

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
    void getAllOrderDetails_ShouldReturnOrderList() throws Exception {
        // Mock service to return a list of ordersDto
        List<OrdersDto> orders = new ArrayList<>();
        orders.add(ordersDto);
        when(orderService.getOrders(any(), any(), any())).thenReturn(orders);

        // Perform GET request and validate the response
        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value("5"))
                .andExpect(jsonPath("$[0].price").value("50"));
    }

    @Test
    void addOrders_ShouldReturnOrderId() throws Exception {
        // Mock service to return a order ID when creating a order
        when(orderService.createOrder(orderRequest)).thenReturn(1L);

        // Perform POST request and validate the response
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}