package be.kdg.sa.orderservice.application;

import be.kdg.sa.orderservice.domain.dish.DishId;
import be.kdg.sa.orderservice.domain.order.*;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orders;

    public OrderService(OrderRepository orders) {
        this.orders = orders;
    }

    public Order findOrderById(OrderId orderId) {
        return orders.findById(orderId)
                .orElseThrow(orderId::notFound);
    }

    public Order addLineToOrder(OrderId orderId, DishId dishId, int amount) {
        Order order = findOrderById(orderId);
        order.addDish(dishId, amount);
        orders.save(order);
        return order;
    }

    public Order openNewOrderAt(UUID restaurantId) {
        RestaurantId resId = new RestaurantId(restaurantId);
        Order order = new Order(resId);
        orders.save(order);
        return order;
    }

    public Order submitOrder(OrderId orderId){
        Order order = findOrderById(orderId);
        order.submit();
        orders.save(order);
        return order;
    }

    public void updateAcceptedOrRejectedOrder(OrderId orderId, boolean isAccepted){
        Order order = findOrderById(orderId);
        order.acceptOrReject(isAccepted);
        orders.save(order);
    }
}
