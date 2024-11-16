package com.food.delivery.fooddelivery.controllers;

import com.food.delivery.fooddelivery.exception.ErrorDetails;
import com.food.delivery.fooddelivery.models.DeliveryDto;
import com.food.delivery.fooddelivery.models.DeliveryRequest;
import com.food.delivery.fooddelivery.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
@SecurityRequirement(name = "bearerAuth")
public class DeliveryEndpoint {

    private final DeliveryService deliveryService;

    public DeliveryEndpoint(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('DELIVERY_PARTNER', 'ADMIN')")
    @Operation(
            summary = "Add a delivery",
            description = "This endpoint will allows to add a delivery. Only Delivery partner and admins are allowed to add a delivery",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery added successfully"),
                    @ApiResponse(responseCode = "404", description = "Delivery mentioned in request does not exist in system",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<Long> addDelivery(@Valid @RequestBody DeliveryRequest deliveryRequest)
    {
        return ResponseEntity.ok().body(deliveryService.createDeliveries(deliveryRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DELIVERY_PARTNER', 'ADMIN')")
    @Operation(
            summary = "Update a delivery",
            description = "This endpoint will allows to update a delivery details. Only Delivery partner and admins are allowed to update the delivery details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delivery updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Delivery does not exists in the system",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<DeliveryDto> updateDelivery(@PathVariable("id") Long delivery_id, @Valid @RequestBody DeliveryDto deliveryDto)
    {
        DeliveryDto updatedDelivery = deliveryService.updateDelivery(delivery_id, deliveryDto);
        return ResponseEntity.ok(updatedDelivery);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER','RESTAURANT_OWNER','ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Fetch deliveries based on filter criteria",
            description = "This endpoint allows to get deliveries based on given criteria. IF no criteria is given, all deliveries are returned",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of deliveries with matching criteria"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<List<DeliveryDto>> getDeliveries(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Timestamp picked_up_at,
            @RequestParam(required = false) Timestamp created_at
            )
    {
        return ResponseEntity.ok().body(deliveryService.getDeliveries(status, picked_up_at, created_at));
    }
}
