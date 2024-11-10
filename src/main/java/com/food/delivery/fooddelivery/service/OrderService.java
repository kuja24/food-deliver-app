package com.food.delivery.fooddelivery.service;

import com.food.delivery.fooddelivery.entity.Menu;
import com.food.delivery.fooddelivery.entity.Orders;
import com.food.delivery.fooddelivery.entity.Restaurant;
import com.food.delivery.fooddelivery.exception.FoodDeliveryException;
import com.food.delivery.fooddelivery.models.*;
import com.food.delivery.fooddelivery.repository.MenuRepository;
import com.food.delivery.fooddelivery.repository.OrderRepository;
import com.food.delivery.fooddelivery.repository.RestaurantRepository;
import com.food.delivery.fooddelivery.util.OrdersSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, MenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
    }

    public List<OrdersDto> getAllOrders() {
        List<OrdersDto> ordersDtos = new ArrayList<>();
        List<Orders> orders = orderRepository.findAll();
        orders.forEach(order -> {
            ordersDtos.add(OrdersDto.builder()
                    .quantity(order.getQuantity())
                    .price(order.getPrice())
                    .build());
        });
        return ordersDtos;
    }

    @Transactional
    public Long createOrder(OrderRequest request) {
        Menu menu = menuRepository.findById(request.getMenu_id())
                .orElseThrow(() -> new FoodDeliveryException("Menu not found", HttpStatus.BAD_REQUEST));
        Orders order = orderRepository.save(buildOrdersEntity(menu, request.getOrders()));
        return order.getOrderId();
        /*Orders order;
        try {
            order = findOrderById(request.getMenu_id());
        } catch (Exception e) {
            throw new FoodDeliveryException("Owner not found. Please enter valid owner id", HttpStatus.BAD_REQUEST);
        }
        //persist restaurant
        Orders res = orderRepository.save(buildOrdersEntity(order, request.getOrders()));
        return res.getOrderId();*/

    }

    @Transactional
    public OrdersDto updateOrder(Long orderId, OrdersDto ordersDto) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new FoodDeliveryException("Order not found with id: " + orderId, HttpStatus.BAD_REQUEST));

        orders.setQuantity(ordersDto.getQuantity());
        orders.setPrice(ordersDto.getPrice());

        // Save the updated entity back to the database
        Orders updatedOrder = orderRepository.save(orders);

        // Convert the updated entity back to DTO and return
        return convertEntityToDto(updatedOrder);
    }

    public List<OrdersDto> getOrders(Long menuId, Integer quantity, Integer price) {
        // Build the specification dynamically
        Specification<Orders> spec = OrdersSpecification.filterByCriteria(menuId, quantity, price);

        // Fetch restaurants using the specification
        List<Orders> orders = orderRepository.findAll(spec);

        // Convert to DTOs
        return orders.stream().map(this::convertEntityToDto).toList();
    }

    public boolean deleteOrdersById(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Orders findOrderById(Long orderId) {
        Orders order;
        try {
            order = orderRepository.findByOrderId(orderId);
        } catch (Exception e) {
            throw new FoodDeliveryException("Order with id: " + orderId, HttpStatus.NOT_FOUND);
        }
        return order;
    }

    private OrdersDto convertEntityToDto(Orders orders) {
        return OrdersDto.builder()
                .quantity(orders.getQuantity())
                .price(orders.getPrice())
                .build();
    }

    private Orders buildOrdersEntity(Menu menu, OrdersDto ordersDto) {
        return Orders.builder()
                .menu(menu)
                .quantity(ordersDto.getQuantity())
                .price(ordersDto.getPrice())
                .build();
    }
}
