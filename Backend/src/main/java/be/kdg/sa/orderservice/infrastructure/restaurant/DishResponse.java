package be.kdg.sa.orderservice.infrastructure.restaurant;

import be.kdg.sa.orderservice.domain.dish.Dish;
import be.kdg.sa.orderservice.domain.dish.DishId;

import java.util.UUID;

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
