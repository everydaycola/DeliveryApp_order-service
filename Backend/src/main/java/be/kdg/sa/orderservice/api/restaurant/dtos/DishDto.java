package be.kdg.sa.orderservice.api.restaurant.dtos;

import be.kdg.sa.orderservice.domain.restaurant.dish.Dish;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record DishDto(UUID id, String name, String description, double price) {
    public static DishDto from(Dish dish){
        return new DishDto(dish.id().id(), dish.name(), dish.description(), dish.price());
    }
}
