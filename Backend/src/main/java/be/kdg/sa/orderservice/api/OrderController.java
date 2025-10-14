package be.kdg.sa.orderservice.api;

import be.kdg.sa.orderservice.application.OrderService;
import be.kdg.sa.orderservice.domain.order.OrderId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orders;

    public OrderController(OrderService orders) {
        this.orders = orders;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable final UUID id) {
        return ResponseEntity.ok(OrderDto.from(orders.findOrderById(new OrderId(id))));
    }
}
