package be.kdg.sa.orderservice.domain.restaurant.dish;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Dish(
        DishId id,
        String name,
        String description,
        double price
) {
}

