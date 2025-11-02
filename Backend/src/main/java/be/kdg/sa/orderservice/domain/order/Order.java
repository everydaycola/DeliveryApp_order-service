package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import be.kdg.sa.orderservice.domain.restaurant.dish.DishId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.annotation.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Slf4j
@Entity
public class Order {
    private final OrderId orderId;
    private OrderStatus status;
    private final List <OrderLine> orderLines;
    private final RestaurantId restaurantId;
    @Setter
    private String comment;
    @Setter
    private OrderContactInfo contactInfo = OrderContactInfo.EMPTY;

    public Order(OrderId orderId, OrderStatus status, List<OrderLine> orderLines, RestaurantId restaurantId, String comment, OrderContactInfo contactInfo) {
        this.orderId = orderId;
        this.status = status;
        this.orderLines = new ArrayList<>(orderLines); // Create mutable copy
        this.restaurantId = restaurantId;
        this.comment = comment;
        this.contactInfo = contactInfo;
    }

    public Order(RestaurantId restaurantId) {
        this.orderId = new OrderId(UUID.randomUUID());
        this.restaurantId = restaurantId;
        this.status = OrderStatus.UNCONFIRMED;
        this.orderLines = new ArrayList<>();
    }

    public void addDish(DishId dishId, int amount) {
        log.info("Adding dish {} with amount {} to order {}", dishId, amount, this.orderId.id());
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
        log.info("Submitting order {}", this.orderId.id());
        this.status.shouldBe(OrderStatus.UNCONFIRMED);
        this.status = OrderStatus.PENDING;
    }

    public void acceptOrReject(boolean accepted, String reason){
        log.info("Accepting or rejecting order {}", this.orderId.id());
        this.status.shouldBe(OrderStatus.PENDING);
        this.comment = reason;
        this.status = accepted ? OrderStatus.ACCEPTED : OrderStatus.DECLINED;
    }

    public void ready(){
        log.info("Order {} is ready", this.orderId.id());
        this.status.shouldBe(OrderStatus.ACCEPTED);
        this.status = OrderStatus.READY;
    }

    public void pickUpForDelivery(){
        log.info("Order {} is ready for pickup", this.orderId.id());
        this.status.shouldBe(OrderStatus.READY);
        this.status = OrderStatus.IN_DELIVERY;
    }

    public void deliver(){
        log.info("Order {} is delivered", this.orderId.id());
        this.status.shouldBe(OrderStatus.IN_DELIVERY);
        this.status = OrderStatus.DELIVERED;
    }
}
