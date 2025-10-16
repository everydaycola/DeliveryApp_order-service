package be.kdg.sa.orderservice.infrastructure.rabbitMQ.handlers;

import be.kdg.sa.orderservice.infrastructure.rabbitMQ.RabbitMQTopology;
import be.kdg.sa.orderservice.infrastructure.rabbitMQ.messages.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloMessageHandler {


    @RabbitListener(queues = RabbitMQTopology.DELIVERY_QUEUE_NAME)
    void onHelloMessageReceived(HelloMessage message) {
        log.info("hello: {}", message);
    }

    @RabbitListener(queues = RabbitMQTopology.RESTAURANT_QUEUE_NAME)
    void onSomethingMessageReceived(HelloMessage message) {
        log.info("something: {}", message);
    }
}
