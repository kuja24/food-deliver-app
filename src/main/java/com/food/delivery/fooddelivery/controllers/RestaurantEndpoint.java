package com.food.delivery.fooddelivery.controllers;

import com.food.delivery.fooddelivery.exception.ErrorDetails;
import com.food.delivery.fooddelivery.models.RestaurantDto;
import com.food.delivery.fooddelivery.models.RestaurantRequest;
import com.food.delivery.fooddelivery.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@SecurityRequirement(name = "bearerAuth")
public class RestaurantEndpoint {

    private final RestaurantService restaurantService;

    public RestaurantEndpoint(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER','RESTAURANT_OWNER','ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Fetch restaurants based on filter criteria",
            description = "This endpoint allows to get restaurants based on given criteria. IF no criteria is given, all restaurants are returned",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of restaurants with matching criteria"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<List<RestaurantDto>> getRestaurants(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String zip,
            @RequestParam(required = false) String cuisineType,
            @RequestParam(required = false) String hoursOfOperation
    ) {
        return ResponseEntity.ok().body(restaurantService.getRestaurants(name, city, state, zip, cuisineType, hoursOfOperation));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('RESTAURANT_OWNER','ADMIN')")
    @Operation(
            summary = "Add a new restaurant",
            description = "This endpoint allows to add restaurants. Only restaurants owners and admins are allowed to add this",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of restaurants with matching criteria"),
                    @ApiResponse(responseCode = "404", description = "Owner mentioned in request does not exist in system",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<Long> addRestaurant(@Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok().body(restaurantService.createRestaurant(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('RESTAURANT_OWNER', 'ADMIN')")
    @Operation(
            summary = "Update restaurant details",
            description = "This endpoint allows to add restaurants. Only restaurants owners and admins are allowed to update",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of restaurants with matching criteria"),
                    @ApiResponse(responseCode = "404", description = "Restaurant mentioned in request does not exist in system",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<RestaurantDto> updateRestaurant(
            @PathVariable("id") Long restaurantId,
            @Valid @RequestBody RestaurantDto restaurantDto) {
        RestaurantDto updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurantDto);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Delete a restaurant",
            description = "This endpoint allows to delete a restaurant. Only Admins have this access",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurant deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Restaurant not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        boolean isDeleted = restaurantService.deleteRestaurantById(id);

        if (isDeleted) {
            return ResponseEntity.ok("Restaurant deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Restaurant not found.");
        }
    }
}
