package com.food.delivery.fooddelivery.controllers;

import com.food.delivery.fooddelivery.models.DeliveryDTO;
import com.food.delivery.fooddelivery.models.DeliveryResponseDTO;
import com.food.delivery.fooddelivery.service.DeliveryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
@SecurityRequirement(name = "bearerAuth")
public class DeliveryEndpoint {

    @Autowired
    private DeliveryService deliveryService;

    // Create new delivery
    @PostMapping
    public ResponseEntity<DeliveryResponseDTO> createDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        DeliveryResponseDTO response = deliveryService.createDelivery(deliveryDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all deliveries
    @GetMapping
    public ResponseEntity<List<DeliveryResponseDTO>> getAllDeliveries() {
        List<DeliveryResponseDTO> deliveries = deliveryService.getAllDeliveries();
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    // Get delivery by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> getDeliveryById(@PathVariable("id") Long id) {
        DeliveryResponseDTO response = deliveryService.getDeliveryById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update delivery status
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> updateDeliveryStatus(@PathVariable("id") Long id,
                                                                    @RequestBody DeliveryDTO deliveryDTO) {
        DeliveryResponseDTO response = deliveryService.updateDeliveryStatus(id, deliveryDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}