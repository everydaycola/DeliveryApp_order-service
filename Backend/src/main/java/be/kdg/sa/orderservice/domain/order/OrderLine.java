package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.dish.DishId;

import java.util.UUID;

public class OrderLine {
    private final UUID id;
    private final int quantity;
    private final DishId dishId;

    public OrderLine(int quantity, DishId dishId) {
        this.id = UUID.randomUUID();
        this.quantity = quantity;
        this.dishId = dishId;
    }

    public UUID getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public DishId getDishId() {
        return dishId;
    }
}
