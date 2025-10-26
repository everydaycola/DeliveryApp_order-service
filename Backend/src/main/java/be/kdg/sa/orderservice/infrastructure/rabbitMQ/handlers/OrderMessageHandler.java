package be.kdg.sa.orderservice.infrastructure.rabbitMQ.handlers;

import be.kdg.sa.orderservice.application.OrderService;
import be.kdg.sa.orderservice.domain.order.OrderId;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderMessageHandler {
    final OrderService orderService;

    public OrderMessageHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-accepted-queue}")
    void onOrderAcceptedMessageReceived(OrderAcceptedMessage message) {
        log.info("Order Accepted Message Received: Order={}", message.orderDto().id());
        orderService.acceptOrRejectOrder(new OrderId(message.orderDto().id()), true);
    }

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-rejected-queue}")
    void onOrderRejectedMessageReceived(OrderRejectedMessage message) {
        log.info("Order Rejected Message Received: Order={}", message.orderDto().id());
        orderService.acceptOrRejectOrder(new OrderId(message.orderDto().id()), false);
    }

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-ready-queue}")
    void onOrderReadyMessageReceived(OrderReadyMessage message) {
        log.info("Order Ready Message Received: Order={}", message.orderDto().id());
        orderService.readyOrder(new OrderId(message.orderDto().id()));
    }

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-picked-up-queue}")
    void onOrderPickedUpMessageReceived(OrderPickedUpMessage message) {
        log.info("Order Pick Up Message Received: Order={}", message.orderDto().id());
        orderService.pickUpOrder(new OrderId(message.orderDto().id()));
    }

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-delivered-queue}")
    void onOrderDeliveredMessageReceived(OrderDeliveredMessage message) {
        log.info("Order Delivered Message Received: Order={}", message.orderDto().id());
        orderService.deliverOrder(new OrderId(message.orderDto().id()));
    }
}
