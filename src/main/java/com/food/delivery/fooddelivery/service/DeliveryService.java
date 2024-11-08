package com.food.delivery.fooddelivery.service;

import java.sql.Timestamp;

import com.food.delivery.fooddelivery.entity.Delivery;
import com.food.delivery.fooddelivery.entity.DeliveryStatus;
import com.food.delivery.fooddelivery.models.DeliveryDTO;
import com.food.delivery.fooddelivery.models.DeliveryResponseDTO;
import com.food.delivery.fooddelivery.repository.DeliveryRepository;
import com.food.delivery.fooddelivery.util.DeliveryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    // Constructor injection
    public DeliveryService(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }

    // Create a new delivery
    public DeliveryResponseDTO createDelivery(DeliveryDTO deliveryDTO) {
        // Convert DeliveryDTO to Delivery entity
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);

        // Save the delivery entity to the database
        Delivery savedDelivery = deliveryRepository.save(delivery);

        // Convert the saved Delivery entity to DeliveryResponseDTO
        return deliveryMapper.toDTO(savedDelivery);
    }

    // Get all deliveries
    public List<DeliveryResponseDTO> getAllDeliveries() {
        // Fetch all deliveries from the repository
        List<Delivery> deliveries = deliveryRepository.findAll();

        // Convert the list of Delivery entities to a list of DeliveryResponseDTO
        return deliveries.stream()
                .map(deliveryMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get delivery by ID
    public DeliveryResponseDTO getDeliveryById(Long id) {
        // Find the delivery by ID, or throw exception if not found
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + id));

        // Convert the Delivery entity to a DeliveryResponseDTO
        return deliveryMapper.toDTO(delivery);
    }

    // Update the status of an existing delivery
    public DeliveryResponseDTO updateDeliveryStatus(Long id, DeliveryDTO deliveryDTO) {
        // Find the delivery by ID, or throw exception if not found
        Delivery existingDelivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + id));

        // Map the updated status from the DTO and apply it to the existing delivery
        try {
            // Get the status as an enum directly from the DTO (assuming getStatus() returns a DeliveryStatus)
            existingDelivery.setStatus(deliveryDTO.getStatus());  // Set the DeliveryStatus enum

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + deliveryDTO.getStatus());
        }

        // Convert Date to Timestamp
        existingDelivery.setPickedUpAt(new Timestamp(deliveryDTO.getPickedUpAt().getTime()));
        existingDelivery.setDeliveredAt(new Timestamp(deliveryDTO.getDeliveredAt().getTime()));

        // Save the updated delivery
        Delivery updatedDelivery = deliveryRepository.save(existingDelivery);

        // Convert the updated delivery entity to a DeliveryResponseDTO
        return deliveryMapper.toDTO(updatedDelivery);
    }
}