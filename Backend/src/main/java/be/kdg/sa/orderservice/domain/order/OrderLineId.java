package be.kdg.sa.orderservice.domain.order;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record OrderLineId(UUID id) {
    public static OrderLineId create() {
        return new OrderLineId(UUID.randomUUID());
    }
}