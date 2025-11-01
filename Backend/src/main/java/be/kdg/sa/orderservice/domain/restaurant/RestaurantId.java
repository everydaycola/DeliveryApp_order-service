package be.kdg.sa.orderservice.domain.restaurant;

import be.kdg.sa.orderservice.domain.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@Slf4j
@ValueObject
public record RestaurantId(UUID id) {
    public static RestaurantId create() {
        return new RestaurantId(UUID.randomUUID());
    }

    public NotFoundException notFound() {
        log.error("restaurant [{}] not found", id);
        return new NotFoundException("restaurant [" + id + "] not found");
    }
}
