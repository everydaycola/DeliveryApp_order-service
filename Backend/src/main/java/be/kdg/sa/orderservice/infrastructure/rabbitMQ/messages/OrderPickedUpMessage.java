package be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages;

import be.kdg.sa.orderservice.api.order.dtos.OrderPickedUpAndDeliveredDto;

public record OrderPickedUpMessage(OrderPickedUpAndDeliveredDto orderDto) {
}
