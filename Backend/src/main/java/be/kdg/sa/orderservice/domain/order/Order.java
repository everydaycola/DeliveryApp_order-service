package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.dish.DishId;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final OrderId orderId;
    private OrderStatus status;
    private final ArrayList<OrderLine> orderLines;
    private RestaurantId restaurantId;

    public Order(OrderId orderId, RestaurantId restaurantId) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.status = OrderStatus.PENDING;
        this.orderLines = new ArrayList<>();
    }


    public void NewOrderLine(int quantity, DishId dishId) {
        var existingOrderLine = this.orderLines.stream()
                .filter(ol -> ol.getDishId().equals(dishId))
                .findFirst();

        if (existingOrderLine.isPresent()) {
            existingOrderLine.get().increaseQuantity(quantity);
        } else {
            this.orderLines.add(new OrderLine(quantity, dishId));
        }

    }

    // somewhat temporary
    public void setStatus(OrderStatus status) {
        this.status = status;
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
