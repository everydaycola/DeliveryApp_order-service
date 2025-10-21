package be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages;

import be.kdg.sa.orderservice.api.order.dtos.OrderDto;

public record OrderPlacedMessage(OrderDto orderDto) {
}
