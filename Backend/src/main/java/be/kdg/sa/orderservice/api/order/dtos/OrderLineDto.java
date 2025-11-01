package be.kdg.sa.orderservice.api.order.dtos;

import be.kdg.sa.orderservice.domain.order.OrderLine;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
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