package com.food.delivery.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menu_id;

    @OneToOne
    @JoinColumn(name = "restaurantId", nullable = false)
    private Restaurant restaurant;

    private String item_name;
    private String description;
    private float price;
    private Boolean is_available;

}
