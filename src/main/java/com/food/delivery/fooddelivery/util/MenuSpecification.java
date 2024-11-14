package com.food.delivery.fooddelivery.util;

import com.food.delivery.fooddelivery.entity.Menu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MenuSpecification {

    public static Specification<Menu> filterByCriteria(String item_name)
    {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(item_name !=null && !item_name.isEmpty())
            {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("item_name")), "%"+ item_name.toLowerCase()+ "%" ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }
}
