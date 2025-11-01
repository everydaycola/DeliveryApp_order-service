package be.kdg.sa.orderservice.api.order;

import be.kdg.sa.orderservice.api.order.dtos.NewOrderDto;
import be.kdg.sa.orderservice.api.order.dtos.NewOrderLineDto;
import be.kdg.sa.orderservice.api.order.dtos.OrderDto;
import be.kdg.sa.orderservice.application.OrderService;
import be.kdg.sa.orderservice.config.RabbitMQProperties;
import be.kdg.sa.orderservice.domain.order.OrderId;
import be.kdg.sa.orderservice.domain.restaurant.dish.DishId;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages.OrderPlacedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    private final OrderService orders;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    public OrderController(OrderService orders, RabbitTemplate rabbitTemplate, RabbitMQProperties rabbitMQProperties) {
        this.orders = orders;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQProperties = rabbitMQProperties;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody final NewOrderDto newOrderDto) {
        log.info("Creating new order for restaurant {}", newOrderDto.restaurantId());
        final var order = orders.openNewOrderAt(UUID.fromString(newOrderDto.restaurantId()));
        return ResponseEntity.ok(OrderDto.from(order));
    }

    @PostMapping("/{orderId}/dishes/{dishId}")
    public ResponseEntity<OrderDto> addMultipleOrder(@PathVariable final UUID orderId,
                                                     @PathVariable final UUID dishId,
                                                     @RequestBody final NewOrderLineDto newOrderLineDto) {
        log.info("Adding dish {} to order {}", dishId, orderId);
        final var order = orders.addLineToOrder(
                new OrderId(orderId),
                new DishId(dishId),
                newOrderLineDto.amount()
        );

        return ResponseEntity.ok(OrderDto.from(order));
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable final UUID id) {
        log.info("Finding order with id {}", id);
        return ResponseEntity.ok(OrderDto.from(orders.findOrderById(new OrderId(id))));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderDto> submitOrder(@PathVariable final UUID orderId){
        log.info("Submitting order {}", orderId);
        final var order = orders.submitOrder(new OrderId(orderId));
        final var dto = OrderDto.from(order);

        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(),
                                      rabbitMQProperties.getOrderPlacedBinding(),
                                      new OrderPlacedMessage(dto));
        
        return ResponseEntity.ok(dto);
    }
}
