package com.food.delivery.fooddelivery.util;
import java.sql.Timestamp;  // Make sure this import is added

import org.springframework.stereotype.Component;

import com.food.delivery.fooddelivery.entity.Delivery;
import com.food.delivery.fooddelivery.models.DeliveryDTO;
import com.food.delivery.fooddelivery.models.DeliveryResponseDTO;
import com.food.delivery.fooddelivery.entity.User;
import com.food.delivery.fooddelivery.entity.Address;
import com.food.delivery.fooddelivery.entity.DeliveryStatus;
import com.food.delivery.fooddelivery.repository.UserRepository; // Import the UserRepository
import com.food.delivery.fooddelivery.repository.AddressRepository; // Import the UserRepository


import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DeliveryMapper {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;  // Inject the AddressRepository

    @Autowired
    public DeliveryMapper(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;  // Set the addressRepository
    }

    // Method to convert DeliveryDTO to Delivery entity
    public Delivery toEntity(DeliveryDTO deliveryDTO) {
        // Fetch the user from the database
        User deliveryPartner = userRepository.findById(deliveryDTO.getDeliveryPartnerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the address from the database
        Address address = addressRepository.findById(deliveryDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        return Delivery.builder()
                .deliveryId(deliveryDTO.getDeliveryId())  // Assuming deliveryId exists in DTO
                .orderId(deliveryDTO.getOrderId())        // Assuming orderId exists in DTO
                .status(deliveryDTO.getStatus())
                .pickedUpAt(new Timestamp(deliveryDTO.getPickedUpAt().getTime()))  // Convert Date to Timestamp
                .deliveredAt(new Timestamp(deliveryDTO.getDeliveredAt().getTime()))  // Convert Date to Timestamp
                .deliveryPartner(deliveryPartner)  // Set the User object
                .address(address)  // Set the address
                .build();
    }

    // Method to convert Delivery entity to DeliveryResponseDTO
    public DeliveryResponseDTO toDTO(Delivery delivery) {
        return DeliveryResponseDTO.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(delivery.getOrderId())  // Assuming orderId is needed in response DTO
                .status(delivery.getStatus())  // Convert Enum to String
                .pickedUpAt(delivery.getPickedUpAt())
                .deliveredAt(delivery.getDeliveredAt())
                .deliveryPartnerId(delivery.getDeliveryPartner().getUserId())  // Assuming partner ID is needed in response DTO
                .addressId(delivery.getAddress().getAddressId())  // Assuming you need the addressId in response
                .build();
    }
}