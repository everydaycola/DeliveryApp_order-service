package be.kdg.sa.orderservice.api.order.dtos;

import be.kdg.sa.orderservice.domain.order.Order;
import be.kdg.sa.orderservice.domain.order.OrderStatus;

import java.util.UUID;

public record OrderAcceptedDto(UUID id, UUID restaurantId) {
    public static OrderAcceptedDto from(Order order){
        return new OrderAcceptedDto(
                order.getOrderId().id(),
                order.getRestaurantId().id()
        );
    }
}
