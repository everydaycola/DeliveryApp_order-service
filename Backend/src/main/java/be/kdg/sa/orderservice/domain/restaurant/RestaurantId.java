package be.kdg.sa.orderservice.domain.restaurant;

import be.kdg.sa.orderservice.domain.NotFoundException;

import java.util.UUID;

public record RestaurantId(UUID id) {
    public static RestaurantId create() {
        return new RestaurantId(UUID.randomUUID());
    }

    public NotFoundException notFound() {
        return new NotFoundException("restaurant [" + id + "] not found");
    }
}
