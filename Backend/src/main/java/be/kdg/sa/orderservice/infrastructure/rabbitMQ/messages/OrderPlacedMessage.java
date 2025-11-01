package be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages;

import be.kdg.sa.orderservice.api.order.dtos.OrderDto;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record OrderPlacedMessage(OrderDto orderDto) {
}
