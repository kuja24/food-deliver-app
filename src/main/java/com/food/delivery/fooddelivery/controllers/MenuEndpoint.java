package com.food.delivery.fooddelivery.controllers;

import com.food.delivery.fooddelivery.entity.Menu;
import com.food.delivery.fooddelivery.exception.ErrorDetails;
import com.food.delivery.fooddelivery.models.MenuDto;
import com.food.delivery.fooddelivery.models.MenuRequest;
import com.food.delivery.fooddelivery.service.MenuService;
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
@RequestMapping("/api/v1/menu")
@SecurityRequirement(name = "bearerAuth")
public class MenuEndpoint {

    private final MenuService menuService;

    public MenuEndpoint(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('RESTAURANT_OWNER', 'ADMIN')")
    @Operation(
            summary = "Add a new menu",
            description = "This endpoint will allows to add menus in the particular restaurant.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menu added successfully"),
                    @ApiResponse(responseCode = "404", description = "Owner mentioned in request does not exist in system",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))

                    }
    )
    public ResponseEntity<Long> addMenu(@Valid @RequestBody MenuRequest menuRequest)
    {
        return ResponseEntity.ok().body(menuService.createMenuItem(menuRequest));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('RESTAURANT_OWNER', 'ADMIN')")
    @Operation(
            summary = "Update Menu details",
            description = "This endpoint will allow to update menus. Only restaurant owners and admin can update",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menu updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Menu does not exists in the system",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<MenuDto> updateMenu(
            @PathVariable("id") Long id,
            @Valid @RequestBody MenuDto menuDto)
    {
        MenuDto updatedMenu = menuService.updateMenu(id, menuDto);
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('RESTAURANT_OWNER', 'ADMIN')")
    @Operation(
            summary = "Delete a menu",
            description = "This endpoint will allow to delete menus. Only restaurant owners and admin can update",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Menu deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Menu not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }
    )
    public ResponseEntity<String> deleteMenu(@PathVariable Long id)
    {
        boolean isMenuDeleted = menuService.deleteMenuById(id);
        if(isMenuDeleted)
        {
            return ResponseEntity.ok("Menu deleted successfully");
        }
        else {
            return ResponseEntity.status(404).body("Menu not found");
        }

    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('CUSTOMER','RESTAURANT_OWNER','ADMIN','DELIVERY_PARTNER')")
    @Operation(
            summary = "Search item based on item_name",
            description = "This endpoint allows to get list of items based on item names",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of item names"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetails.class)))
            }

    )
    public ResponseEntity<List<MenuDto>> searchMenuItems(@RequestParam String item_name)
    {
        return ResponseEntity.ok().body(menuService.searchMenuItems(item_name));
    }
}
