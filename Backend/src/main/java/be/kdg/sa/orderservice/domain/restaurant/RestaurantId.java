package be.kdg.sa.orderservice.domain.restaurant;

import java.util.UUID;

public record RestaurantId(UUID id) {
    public static RestaurantId create() {
        return new RestaurantId(UUID.randomUUID());
    }
}
