package be.kdg.sa.orderservice.domain.order;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record OrderId(UUID id) {
    public static OrderId create() {
        return new OrderId(UUID.randomUUID());
    }
}
