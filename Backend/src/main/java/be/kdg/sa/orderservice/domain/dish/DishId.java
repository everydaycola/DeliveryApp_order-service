package be.kdg.sa.orderservice.domain.dish;

import be.kdg.sa.orderservice.domain.NotFoundException;
import org.springframework.util.Assert;

import java.util.UUID;

public record DishId(UUID id) {
    public DishId {
        Assert.notNull(id, "id cannot be null");
    }

    public NotFoundException notFound() {
        return new NotFoundException("Dish [" + id + "] not found");
    }

    public static DishId create() {
        return new DishId(UUID.randomUUID());
    }
}
