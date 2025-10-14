package be.kdg.sa.orderservice.application;

import be.kdg.sa.orderservice.domain.order.Order;
import be.kdg.sa.orderservice.domain.order.OrderId;
import be.kdg.sa.orderservice.domain.order.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
