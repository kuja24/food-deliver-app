package com.food.delivery.fooddelivery.controllers;

import com.food.delivery.fooddelivery.exception.ErrorDetails;
import com.food.delivery.fooddelivery.models.OrderRequest;
import com.food.delivery.fooddelivery.models.OrdersDto;
import com.food.delivery.fooddelivery.service.OrderService;
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
@RequestMapping("/api/v1/orders")
@SecurityRequirement(name = "bearerAuth")
public class OrdersEndPoint {

    private final OrderService orderService;

    public OrdersEndPoint(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER','RESTAURANT_OWNER','ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Fetch orders based on filter criteria",
            description = "This endpoint allows to get orders based on given criteria. IF no criteria is given, all orders are returned",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of orders with matching criteria"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<List<OrdersDto>> getOrders(
            @RequestParam(required = false) Long menuId,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Integer price
    ) {
        return ResponseEntity.ok().body(orderService.getOrders(menuId, quantity, price));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('RESTAURANT_OWNER','ADMIN')")
    @Operation(
            summary = "Add a new order",
            description = "This endpoint allows to add orders. Only restaurants owners and admins are allowed to add this",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of orders with matching criteria"),
                    @ApiResponse(responseCode = "404", description = "Owner mentioned in request does not exist in system",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<Long> addOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok().body(orderService.createOrder(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('RESTAURANT_OWNER', 'ADMIN')")
    @Operation(
            summary = "Update order details",
            description = "This endpoint allows to add orders. Only restaurants owners and admins are allowed to update",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of orders with matching criteria"),
                    @ApiResponse(responseCode = "404", description = "order mentioned in request does not exist in system",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<OrdersDto> updateOrder(
            @PathVariable("id") Long orderId,
            @Valid @RequestBody OrdersDto orderDto) {
        OrdersDto updatedOrder = orderService.updateOrder(orderId, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Delete a order",
            description = "This endpoint allows to delete a order. Only Admins have this access",
            responses = {
                    @ApiResponse(responseCode = "200", description = "order deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "order not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        boolean isDeleted = orderService.deleteOrdersById(id);

        if (isDeleted) {
            return ResponseEntity.ok("order deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("order not found.");
        }
    }
}
