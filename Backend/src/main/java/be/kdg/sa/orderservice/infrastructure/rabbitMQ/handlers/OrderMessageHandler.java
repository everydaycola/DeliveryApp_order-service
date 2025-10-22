package be.kdg.sa.orderservice.infrastructure.rabbitMQ.handlers;

import be.kdg.sa.orderservice.application.OrderService;
import be.kdg.sa.orderservice.domain.order.OrderId;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.RabbitMQTopology;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages.OrderAcceptedMessage;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages.OrderRejectedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderMessageHandler {
    OrderService orderService;

    public OrderMessageHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMQTopology.ORDER_ACCEPTED_QUEUE_NAME)
    void onOrderAcceptedMessageReceived(OrderAcceptedMessage message) {
        log.info("Order Accepted Message Received: Order={}", message.orderDto().id());
        orderService.updateAcceptedOrRejectedOrder(new OrderId(message.orderDto().id()), true);
    }

    @RabbitListener(queues = RabbitMQTopology.ORDER_REJECTED_QUEUE_NAME)
    void onOrderRejectedMessageReceived(OrderRejectedMessage message) {
        log.info("Order Rejected Message Received: Order={}", message.orderDto().id());
        orderService.updateAcceptedOrRejectedOrder(new OrderId(message.orderDto().id()), false);
    }
}
