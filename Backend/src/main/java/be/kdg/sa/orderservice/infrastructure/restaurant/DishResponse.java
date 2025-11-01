package be.kdg.sa.orderservice.infrastructure.restaurant;

import be.kdg.sa.orderservice.domain.restaurant.dish.Dish;
import be.kdg.sa.orderservice.domain.restaurant.dish.DishId;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
record DishResponse(
        String id,
        String name,
        String description,
        double price
) {
    public Dish toDish() {
        return new Dish(
                new DishId(UUID.fromString(this.id)),
                this.name,
                this.description,
                this.price
        );
    }
}
