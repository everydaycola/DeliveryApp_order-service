package be.kdg.sa.orderservice.api.restaurant.dtos;


import be.kdg.sa.orderservice.domain.dish.Dish;

import java.util.UUID;

public record DishDto(UUID id, String name, String description, double price) {
    public static DishDto from(Dish dish){
        return new DishDto(dish.getId().id(), dish.getName(), dish.getDescription(), dish.getPrice());
    }
}
