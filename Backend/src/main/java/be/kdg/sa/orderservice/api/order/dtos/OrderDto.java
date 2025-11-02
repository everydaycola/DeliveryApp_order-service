package be.kdg.sa.orderservice.api.order.dtos;

import be.kdg.sa.orderservice.domain.order.Order;
import be.kdg.sa.orderservice.domain.order.OrderContactInfo;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.List;

@ValueObject
public record OrderDto(
        String orderId,
        String restaurantId,
        String status,
        List<OrderLineDto> orderLines,
        String comment,
        SubmittedOrderDto contactInfo
) {
    public static OrderDto from(final Order order) {
        return new OrderDto(
                order.getOrderId().id().toString(),
                order.getRestaurantId().id().toString(),
                order.getStatus().name(),
                order.getOrderLines().stream().map(OrderLineDto::from).toList(),
                order.getComment(),
                SubmittedOrderDto.from(order.getContactInfo())
        );
    }
}
