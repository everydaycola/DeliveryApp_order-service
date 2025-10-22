package be.kdg.sa.orderservice.api.order;

import be.kdg.sa.orderservice.api.order.dtos.NewOrderDto;
import be.kdg.sa.orderservice.api.order.dtos.OrderDto;
import be.kdg.sa.orderservice.api.order.dtos.OrderLineDto;
import be.kdg.sa.orderservice.application.OrderService;
import be.kdg.sa.orderservice.domain.dish.DishId;
import be.kdg.sa.orderservice.domain.order.Order;
import be.kdg.sa.orderservice.domain.order.OrderId;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.RabbitMQTopology;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages.OrderPlacedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orders;
    private final RabbitTemplate rabbitTemplate;

    public OrderController(OrderService orders, RabbitTemplate rabbitTemplate) {
        this.orders = orders;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody final NewOrderDto newOrderDto) {
        Order order = orders.openNewOrderAt(UUID.fromString(newOrderDto.restaurantId()));
        return ResponseEntity.ok(OrderDto.from(order));
    }

    @PostMapping("/{orderId}/dishes/{dishId}")
    public ResponseEntity<OrderDto> addMultipleOrder(@PathVariable final UUID orderId,
                                                     @PathVariable final UUID dishId,
                                                     @RequestBody final OrderLineDto orderLineDto) {
        Order order = orders.addLineToOrder(
                new OrderId(orderId),
                new DishId(dishId),
                orderLineDto.amount()
        );

        return ResponseEntity.ok(OrderDto.from(order));
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable final UUID id) {
        return ResponseEntity.ok(OrderDto.from(orders.findOrderById(new OrderId(id))));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderDto> submitOrder(@PathVariable final UUID orderId){
        Order order = orders.submitOrder(new OrderId(orderId));
        OrderDto dto = OrderDto.from(order);

        rabbitTemplate.convertAndSend(RabbitMQTopology.KDG_EXCHANGE_NAME, "order.placed", new OrderPlacedMessage(dto));
        
        return ResponseEntity.ok(dto);
    }
}
