package be.kdg.sa.orderservice.api.order.dtos;

import java.util.UUID;

public record OrderMessagingDto(UUID id, UUID restaurantId) {
}
