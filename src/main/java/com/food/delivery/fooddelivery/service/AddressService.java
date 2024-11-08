package com.food.delivery.fooddelivery.service;

import com.food.delivery.fooddelivery.entity.Address;
import com.food.delivery.fooddelivery.models.AddressDto;
import com.food.delivery.fooddelivery.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address saveAddress(AddressDto addressDto) {
        Address address = Address.builder()
                .addressLine(addressDto.getAddressLine())
                .state(addressDto.getState())
                .city(addressDto.getCity())
                .zipCode(addressDto.getZip())
                .build();
        address = addressRepository.save(address);
        return address;
    }
}
