package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.dish.DishId;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Slf4j
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

    public void addDish(DishId dishId, int amount) {
        this.status.shouldBe(OrderStatus.UNCONFIRMED);
        orderLines.stream()
                .filter(ol -> ol.getDishId().equals(dishId))
                .findFirst()
                .ifPresentOrElse(
                        existing -> existing.increaseQuantity(amount),
                        () -> this.orderLines.add(new OrderLine(dishId, amount))
                );
    }

    public void submit(){
        this.status.shouldBe(OrderStatus.UNCONFIRMED);
        this.status = OrderStatus.PENDING;
        log.info("Order {} status set to {}", this.orderId.id(),this.status );
    }

    public void acceptOrReject(boolean accepted){
        this.status.shouldBe(OrderStatus.PENDING);
        this.status = accepted ? OrderStatus.ACCEPTED : OrderStatus.DECLINED;
        log.info("Order {} status set to {}", this.orderId.id(),this.status );
    }

    public void ready(){
        this.status.shouldBe(OrderStatus.ACCEPTED);
        this.status = OrderStatus.READY;
        log.info("Order {} status set to {}", this.orderId.id(),this.status );
    }

    public void pickUpForDelivery(){
        this.status.shouldBe(OrderStatus.READY);
        this.status = OrderStatus.IN_DELIVERY;
        log.info("Order {} status set to {}", this.orderId.id(),this.status );
    }

    public void deliver(){
        this.status.shouldBe(OrderStatus.IN_DELIVERY);
        this.status = OrderStatus.DELIVERED;
        log.info("Order {} status set to {}", this.orderId.id(),this.status );
    }
}
