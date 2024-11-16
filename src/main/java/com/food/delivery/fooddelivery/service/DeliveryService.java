package com.food.delivery.fooddelivery.service;

import com.food.delivery.fooddelivery.entity.Delivery;
import com.food.delivery.fooddelivery.entity.Orders;
import com.food.delivery.fooddelivery.entity.User;
import com.food.delivery.fooddelivery.exception.FoodDeliveryException;
import com.food.delivery.fooddelivery.models.DeliveryDto;
import com.food.delivery.fooddelivery.models.DeliveryRequest;
import com.food.delivery.fooddelivery.repository.DeliveryRepository;
import com.food.delivery.fooddelivery.repository.OrderRepository;
import com.food.delivery.fooddelivery.util.DeliverySpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.query.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;

    public DeliveryService(DeliveryRepository deliveryRepository, OrderRepository orderRepository, UserService userService) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public Long createDeliveries(DeliveryRequest deliveryRequest)
    {
        Orders order = orderRepository.findById(deliveryRequest.getOrderId())
                .orElseThrow(() -> new FoodDeliveryException("Order is not present", HttpStatus.BAD_REQUEST));
        User user;
        try {
            user = userService.findUserByUserId(deliveryRequest.getOwnerId());
        } catch (Exception e) {
            throw new FoodDeliveryException("User not found. Please enter valid owner id", HttpStatus.BAD_REQUEST);
        }

        Delivery delivery = deliveryRepository.save(buildDeliveryEntity(user, order, deliveryRequest.getDeliveryDto()));
        return delivery.getDelivery_id();
    }

    public DeliveryDto updateDelivery(Long delivery_id, DeliveryDto deliveryDto)
    {
        Delivery delivery = deliveryRepository.findById(delivery_id)
                .orElseThrow(() -> new FoodDeliveryException("Delivery not found with id: "+delivery_id, HttpStatus.BAD_REQUEST));
        delivery.setStatus(deliveryDto.getStatus());
        delivery.setPicked_up_at(deliveryDto.getPicked_up_at());
        delivery.setCreated_at(deliveryDto.getCreated_at());
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        return convertEntityToDto(updatedDelivery);
    }

    public List<DeliveryDto> getDeliveries(String status, Timestamp picked_up_at, Timestamp created_at)
    {
        Specification<Delivery> spec = DeliverySpecification.filterByCriteria(status, picked_up_at, created_at);
        List<Delivery> deliveries = deliveryRepository.findAll(spec);
        return deliveries.stream().map(this::convertEntityToDto).toList();
    }

    private DeliveryDto convertEntityToDto(Delivery updatedDelivery) {
        return DeliveryDto.builder()
                .status(updatedDelivery.getStatus())
                .picked_up_at(updatedDelivery.getPicked_up_at())
                .created_at(updatedDelivery.getCreated_at())
                .build();
    }


    private Delivery buildDeliveryEntity(User user, Orders order, @Valid @NotNull DeliveryDto deliveryDto) {
        return Delivery.builder()
                .owner(user)
                .orders(order)
                .status(deliveryDto.getStatus())
                .picked_up_at(deliveryDto.getPicked_up_at())
                .created_at(deliveryDto.getCreated_at())
                .build();
    }
}
