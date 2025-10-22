package be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages;

import be.kdg.sa.orderservice.api.order.dtos.OrderAcceptedOrRejectedDto;

public record OrderAcceptedMessage(OrderAcceptedOrRejectedDto orderDto) {
}
