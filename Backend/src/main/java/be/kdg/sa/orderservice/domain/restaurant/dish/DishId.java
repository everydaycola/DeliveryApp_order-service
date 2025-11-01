package be.kdg.sa.orderservice.domain.restaurant.dish;

import be.kdg.sa.orderservice.domain.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

import java.util.UUID;

@Slf4j
@ValueObject
public record DishId(UUID id) {
    public DishId {
        Assert.notNull(id, "id cannot be null");
    }

    public NotFoundException notFound() {
        log.error("Dish [{}] not found", id);
        return new NotFoundException("Dish [" + id + "] not found");
    }

    public static DishId create() {
        return new DishId(UUID.randomUUID());
    }
}
