package com.food.delivery.fooddelivery.util;

import com.food.delivery.fooddelivery.entity.Orders;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrdersSpecification {

    public static Specification<Orders> filterByCriteria(Long menuId, Integer quantity, Integer price) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by menuId
            if (menuId != null) {
                predicates.add(criteriaBuilder.equal(root.get("menuId"), menuId));
            }

            // Filter by quantity
            if (quantity != null) {
                predicates.add(criteriaBuilder.equal(root.get("quantity"), quantity));
            }

            // Filter by price
            if (price != null) {
                predicates.add(criteriaBuilder.equal(root.get("price"), price));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
