package be.kdg.sa.orderservice.api;

import be.kdg.sa.orderservice.domain.order.OrderLine;

public record OrderLineDto(
        String dishId,
        int amount
) {
    public static OrderLineDto from(final OrderLine orderLine) {
        return new OrderLineDto(
                orderLine.getDishId().id().toString(),
                orderLine.getQuantity()
        );
    }
}
