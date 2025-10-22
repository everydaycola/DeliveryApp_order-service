package be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages;

import be.kdg.sa.orderservice.api.order.dtos.OrderMessagingDto;

public record OrderReadyMessage(OrderMessagingDto orderDto) {
}
