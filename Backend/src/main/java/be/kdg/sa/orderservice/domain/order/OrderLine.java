package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.dish.DishId;

import java.util.UUID;

public class OrderLine {
    private final OrderLineId id;
    private int quantity;
    private final DishId dishId;

    public OrderLine(int quantity, DishId dishId) {
        this.id = new OrderLineId(UUID.randomUUID());
        this.quantity = quantity;
        this.dishId = dishId;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public OrderLineId getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public DishId getDishId() {
        return dishId;
    }
}
