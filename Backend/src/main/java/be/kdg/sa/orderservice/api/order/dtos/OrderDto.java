package be.kdg.sa.orderservice.api.order.dtos;

import be.kdg.sa.orderservice.domain.order.Order;

import java.util.List;

public record OrderDto(
        String orderId,
        String restaurantId,
        String status,
        List<OrderLineDto> orderLines
) {
    public static OrderDto from(final Order order) {
        return new OrderDto(
                order.getOrderId().id().toString(),
                order.getRestaurantId().id().toString(),
                order.getStatus().name(),
                order.getOrderLines().stream().map(OrderLineDto::from).toList()
        );
    }
}
