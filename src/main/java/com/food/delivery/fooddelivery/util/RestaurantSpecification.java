package com.food.delivery.fooddelivery.util;

import com.food.delivery.fooddelivery.entity.Restaurant;
import org.springframework.data.jpa.domain.Specification;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


public class RestaurantSpecification {

    public static Specification<Restaurant> filterByCriteria(String name, String addressLine, String city, String state, String zip, String cuisineType, String hoursOfOperation) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by name
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if(addressLine !=null && !addressLine.isEmpty())
            {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address").get("addressLine")), "%" + addressLine.toLowerCase() + "%")); //Pooja
            }

            // Filter by city
            if (city != null && !city.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address").get("city")), "%" + city.toLowerCase() + "%"));
            }

            // Filter by state
            if (state != null && !state.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address").get("state")), "%" + state.toLowerCase() + "%"));
            }

            // Filter by zip
            if (zip != null && !zip.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("address").get("postalCode"), zip));
            }

            // Filter by cuisineType
            if (cuisineType != null && !cuisineType.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("cuisineType"), cuisineType));
            }

            // Filter by hoursOfOperation (assuming it's a string date range or specific time format)
            if (hoursOfOperation != null && !hoursOfOperation.isEmpty()) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                try {
                    Date hours = formatter.parse(hoursOfOperation);
                    predicates.add(criteriaBuilder.equal(root.get("hoursOfOperation"), hours));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
