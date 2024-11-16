package com.food.delivery.fooddelivery.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.fooddelivery.TestSecurityConfig;
import com.food.delivery.fooddelivery.models.DeliveryDto;
import com.food.delivery.fooddelivery.models.DeliveryRequest;
import com.food.delivery.fooddelivery.service.DeliveryService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class DeliveryEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryService deliveryService;

    @Autowired
    private ObjectMapper objectMapper;

    private DeliveryDto deliveryDto;
    private DeliveryRequest deliveryRequest;

    @BeforeEach
    void setUp()
    {
        deliveryDto = new DeliveryDto();
        deliveryDto.setStatus("Picked up");
        deliveryDto.setPicked_up_at(Timestamp.valueOf("2024-11-14 15:30:00"));
        deliveryDto.setCreated_at(Timestamp.valueOf("2024-11-14 15:30:00"));

        deliveryRequest = new DeliveryRequest();
        deliveryRequest.setDeliveryDto(deliveryDto);
        deliveryRequest.setOrderId(1L);
        deliveryRequest.setOwnerId(1L);

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
    void getAllDeliveries_ShouldReturnAllDeliveries() throws Exception
    {
        List<DeliveryDto> deliveries = new ArrayList<>();
        deliveries.add(deliveryDto);
        when(deliveryService.getDeliveries(any(), any(), any())).thenReturn(deliveries);

        mockMvc.perform(get("/api/v1/delivery")
                .param("status","Picked up"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("Picked up"))
                .andExpect(jsonPath("$[0].picked_up_at").value("2024-11-14T10:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].created_at").value("2024-11-14T10:00:00.000+00:00"));

    }

    @Test
    void addDelivery_ShouldReturnDeliveryId() throws Exception
    {
        when(deliveryService.createDeliveries(deliveryRequest)).thenReturn(1L);

        mockMvc.perform(post("/api/v1/delivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deliveryRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }


}
