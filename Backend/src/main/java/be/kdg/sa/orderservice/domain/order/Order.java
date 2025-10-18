package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Order {
    private final OrderId orderId;
    private OrderStatus status;
    private final List <OrderLine> orderLines;
    private final RestaurantId restaurantId;

    public Order(RestaurantId restaurantId) {
        this.orderId = new OrderId(UUID.randomUUID());
        this.restaurantId = restaurantId;
        this.status = OrderStatus.PENDING;
        this.orderLines = new ArrayList<>();
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.stream()
                  .filter(ol -> ol.getDishId().equals(orderLine.getDishId()))
                  .findFirst()
                  .ifPresentOrElse(
                          existing -> existing.increaseQuantity(orderLine.getQuantity()),
                          () -> orderLines.add(orderLine)
                );
    }

    // somewhat temporary
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
