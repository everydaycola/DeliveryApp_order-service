package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.dish.DishId;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;

import java.util.List;

public class Order {
    private final OrderId orderId;
    private OrderStatus status;
    private List<OrderLine> orderLines;
    private RestaurantId restaurantId;

    public Order(OrderId orderId) {
        this.orderId = orderId;
        this.status = OrderStatus.PENDING;
        this.orderLines = List.of();
    }


    public void NewOrderLine(int quantity, DishId dishId) {
        if (this.orderLines.stream().anyMatch(ol -> ol.getDishId().equals(dishId)))
        this.orderLines.add(new OrderLine(quantity, dishId));
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List <OrderLine> getOrderLines() {
        return orderLines;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }
}
