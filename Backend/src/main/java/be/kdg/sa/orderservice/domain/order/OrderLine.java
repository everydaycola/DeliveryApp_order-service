package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.dish.DishId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderLine {
    private final OrderLineId id;
    private int quantity;
    private final DishId dishId;

    public OrderLine( DishId dishId, int quantity) {
        this.id = new OrderLineId(UUID.randomUUID());
        this.quantity = quantity;
        this.dishId = dishId;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}
