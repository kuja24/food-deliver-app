package com.food.delivery.fooddelivery.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.delivery.fooddelivery.TestSecurityConfig;
import com.food.delivery.fooddelivery.models.MenuDto;
import com.food.delivery.fooddelivery.models.MenuRequest;
import com.food.delivery.fooddelivery.service.MenuService;
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
public class MenuEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @Autowired
    private ObjectMapper objectMapper;

    private MenuRequest menuRequest;
    private MenuDto menuDto;

    @BeforeEach
    void setUp()
    {
        menuDto = new MenuDto();
        menuDto.setItem_name("Thali");
        menuDto.setDescription("This is Veg Thali");
        menuDto.setPrice(200.00f);
        menuDto.setIs_available(true);

        menuRequest = new MenuRequest();
        menuRequest.setRestaurantId(1L);
        menuRequest.setMenuDto(menuDto);
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
    void getMenu_ShouldReturnItemName() throws Exception
    {
        List<MenuDto> menuDtoList = new ArrayList<>();
        menuDtoList.add(menuDto);
        when(menuService.searchMenuItems(any())).thenReturn(menuDtoList);

        mockMvc.perform(get("/api/v1/menu/search")
                .param("item_name","Thali"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].item_name").value("Thali"))
                .andExpect(jsonPath("$[0].description").value("This is Veg Thali"))
                .andExpect(jsonPath("$[0].price").value(200.00f))
                .andExpect(jsonPath("$[0].is_available").value(true));

    }

    @Test
    void addMenu_ShouldReturnMenuId() throws Exception
    {
        when(menuService.createMenuItem(menuRequest)).thenReturn(1L);

        mockMvc.perform(post("/api/v1/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuRequest)))
                .andExpect(status().isOk())
            .andExpect(content().string("1"));
    }
}
