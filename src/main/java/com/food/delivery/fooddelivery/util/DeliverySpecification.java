package com.food.delivery.fooddelivery.util;

import com.food.delivery.fooddelivery.entity.Delivery;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DeliverySpecification {

    public static Specification<Delivery> filterByCriteria(String status, Timestamp picked_up_at, Timestamp created_at)
    {
        return (root, query, criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();
            if(status!=null && !status.isEmpty())
            {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + status.toLowerCase() + "%"));
            }

            if(picked_up_at!=null)
            {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("picked_up_at")), "%" ));
            }

            if(created_at!=null)
            {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("created_at")), "%" ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
