package be.kdg.sa.common_messaging;

import be.kdg.sa.orderservice.api.order.dtos.OrderAcceptedOrRejectedDto;

public record OrderAcceptedMessage(OrderAcceptedOrRejectedDto orderDto) {
}
