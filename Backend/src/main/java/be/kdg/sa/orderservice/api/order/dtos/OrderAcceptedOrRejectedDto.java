package be.kdg.sa.orderservice.api.order.dtos;

import java.util.UUID;

public record OrderAcceptedOrRejectedDto(UUID id, UUID restaurantId) {
}
