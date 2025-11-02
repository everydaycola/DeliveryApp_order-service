package be.kdg.sa.orderservice.api.order.dtos;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record OrderMessagingDto(UUID id, UUID restaurantId, String comment) {
}
