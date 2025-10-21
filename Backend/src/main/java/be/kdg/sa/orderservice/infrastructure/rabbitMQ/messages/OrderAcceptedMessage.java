package be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages;

import be.kdg.sa.orderservice.api.order.dtos.OrderAcceptedDto;

public record OrderAcceptedMessage(OrderAcceptedDto orderDto) {
}
