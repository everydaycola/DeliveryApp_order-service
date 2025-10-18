package be.kdg.sa.orderservice.domain.order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(OrderId orderId);
    void save(Order order);
}
