package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Order {
    private final OrderId orderId;
    private OrderStatus status;
    private final List <OrderLine> orderLines;
    private final RestaurantId restaurantId;

    public Order(OrderId orderId, OrderStatus status, List<OrderLine> orderLines, RestaurantId restaurantId) {
        this.orderId = orderId;
        this.status = status;
        this.orderLines = new ArrayList<>(orderLines); // Create mutable copy
        this.restaurantId = restaurantId;
    }

    public Order(RestaurantId restaurantId) {
        this.orderId = new OrderId(UUID.randomUUID());
        this.restaurantId = restaurantId;
        this.status = OrderStatus.UNCONFIRMED;
        this.orderLines = new ArrayList<>();
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.stream()
                  .filter(ol -> ol.getDishId().equals(orderLine.getDishId()))
                  .findFirst()
                  .ifPresentOrElse(
                          existing -> existing.increaseQuantity(orderLine.getQuantity()),
                          () -> this.orderLines.add(orderLine)
                );
    }

    public void submit(){
        if(this.status == OrderStatus.UNCONFIRMED){
            this.status = OrderStatus.PENDING;
        } else {
            throw new IllegalStateException("Order " + this.orderId + " status is not UNCONFIRMED, cannot be set to PENDING.");
        }
    }

    // somewhat temporary
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
