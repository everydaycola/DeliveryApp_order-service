package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@Slf4j
@ValueObject
public record OrderId(UUID id) {
    public static OrderId create() {
        return new OrderId(UUID.randomUUID());
    }

    public NotFoundException notFound() {
        log.error("Order [{}] not found", id);
        return new NotFoundException("Order [" + id + "] not found");
    }
}
