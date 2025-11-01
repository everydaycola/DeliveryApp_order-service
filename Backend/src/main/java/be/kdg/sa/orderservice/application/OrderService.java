package be.kdg.sa.orderservice.application;

import be.kdg.sa.orderservice.domain.order.Order;
import be.kdg.sa.orderservice.domain.order.OrderId;
import be.kdg.sa.orderservice.domain.order.OrderRepository;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import be.kdg.sa.orderservice.domain.restaurant.dish.DishId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class OrderService {

    private final OrderRepository orders;

    public OrderService(OrderRepository orders) {
        this.orders = orders;
    }

    public Order findOrderById(OrderId orderId) {
        log.info("Finding order with id {}", orderId);
        return orders.findById(orderId)
                .orElseThrow(orderId::notFound);
    }

    public Order addLineToOrder(OrderId orderId, DishId dishId, int amount) {
        log.info("Adding line to order with id {} for dish with id {}", orderId, dishId);
        final var order = findOrderById(orderId);
        order.addDish(dishId, amount);
        orders.save(order);
        return order;
    }

    public Order openNewOrderAt(UUID restaurantId) {
        log.info("Opening new order for restaurant with id {}", restaurantId);
        final var resId = new RestaurantId(restaurantId);
        final var order = new Order(resId);
        orders.save(order);
        return order;
    }

    public Order submitOrder(OrderId orderId){
        log.info("Submitting order with id {}", orderId);
        final var order = findOrderById(orderId);
        order.submit();
        orders.save(order);
        return order;
    }

    public void acceptOrRejectOrder(OrderId orderId, boolean isAccepted){
        log.info("Accepting or rejecting order with id {}", orderId);
        final var order = findOrderById(orderId);
        order.acceptOrReject(isAccepted);
        orders.save(order);
    }

    public void readyOrder(OrderId orderId){
        log.info("Readying order with id {}", orderId);
        final var order = findOrderById(orderId);
        order.ready();
        orders.save(order);
    }

    public void pickUpOrder(OrderId orderId){
        log.info("Picking up order with id {}", orderId);
        Order order = findOrderById(orderId);
        order.pickUpForDelivery();
        orders.save(order);
    }

    public void deliverOrder(OrderId orderId){
        log.info("Delivering order with id {}", orderId);
        Order order = findOrderById(orderId);
        order.deliver();
        orders.save(order);
    }
}
