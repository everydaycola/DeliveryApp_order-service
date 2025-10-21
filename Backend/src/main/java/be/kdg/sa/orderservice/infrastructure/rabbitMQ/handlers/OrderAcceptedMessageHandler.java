package be.kdg.sa.orderservice.infrastructure.rabbitMQ.handlers;

import be.kdg.sa.orderservice.application.OrderService;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.RabbitMQTopology;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages.OrderAcceptedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderAcceptedMessageHandler {
    OrderService orderService;

    public OrderAcceptedMessageHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMQTopology.RESTAURANT_ACCEPTED_QUEUE_NAME)
    void onOrderAcceptedMessageReceived(OrderAcceptedMessage message) {
        log.info("Order Accepted Message Received: Order={}", message.orderDto().orderId());


    }
}
