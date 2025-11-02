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

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-accepted-order-queue}")
    void onOrderAcceptedMessageReceived(OrderAcceptedMessage message) {
        log.info("Order Accepted Message Received: Order={}", message.orderDto().id());
        try {
            orderService.acceptOrRejectOrder(new OrderId(message.orderDto().id()), true, "");
        } catch (IllegalStateException e) {
            log.error("Cannot process order acceptance for order {}: {}", message.orderDto().id(), e.getMessage());
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-rejected-queue}")
    void onOrderRejectedMessageReceived(OrderRejectedMessage message) {
        log.info("Order Rejected Message Received: Order={}", message.orderDto().id());
        try {
            orderService.acceptOrRejectOrder(new OrderId(message.orderDto().id()), false, message.orderDto().comment());
        } catch (IllegalStateException e) {
            log.error("Cannot process order rejection for order {}: {}", message.orderDto().id(), e.getMessage());
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-ready-order-queue}")
    void onOrderReadyMessageReceived(OrderReadyMessage message) {
        log.info("Order Ready Message Received: Order={}", message.orderDto().id());
        try {
            orderService.readyOrder(new OrderId(message.orderDto().id()));
        } catch (IllegalStateException e) {
            log.error("Cannot process order readiness for order {}: {}", message.orderDto().id(), e.getMessage());
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-picked-up-queue}")
    void onOrderPickedUpMessageReceived(OrderPickedUpMessage message) {
        log.info("Order Pick Up Message Received: Order={}", message.orderDto().id());
        try {
            orderService.pickUpOrder(new OrderId(message.orderDto().id()));
        } catch (IllegalStateException e) {
            log.error("Cannot process order pickup for order {}: {}", message.orderDto().id(), e.getMessage());
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.kdg.order-delivered-queue}")
    void onOrderDeliveredMessageReceived(OrderDeliveredMessage message) {
        log.info("Order Delivered Message Received: Order={}", message.orderDto().id());
        try {
            orderService.deliverOrder(new OrderId(message.orderDto().id()));
        } catch (IllegalStateException e) {
            log.error("Cannot process order delivery for order {}: {}", message.orderDto().id(), e.getMessage());
        }
    }
}
