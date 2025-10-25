package be.kdg.sa.orderservice.infrastructure.rabbitMQ;

import be.kdg.sa.orderservice.config.RabbitMQProperties;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {

    private final RabbitMQProperties properties;

    public RabbitMQTopology(RabbitMQProperties properties) {
        this.properties = properties;
    }

    @Bean
    TopicExchange kdgExchange() {
        return new TopicExchange(properties.getExchangeName());
    }

    @Bean
    Queue orderAcceptedQueue() {
        return QueueBuilder
                .nonDurable(properties.getOrderAcceptedQueue())
                .build();
    }

    @Bean
    Queue orderRejectedQueue() {
        return QueueBuilder
                .nonDurable(properties.getOrderRejectedQueue())
                .build();
    }

    @Bean
    Queue orderReadyQueue() {
        return QueueBuilder
                .nonDurable(properties.getOrderReadyQueue())
                .build();
    }

    @Bean
    Queue orderPickedUpQueue() {
        return QueueBuilder
                .nonDurable(properties.getOrderPickedUpQueue())
                .build();
    }

    @Bean
    Queue orderDeliveredQueue() {
        return QueueBuilder
                .nonDurable(properties.getOrderDeliveredQueue())
                .build();
    }

    @Bean
    Binding orderAcceptedBinding() {
        return BindingBuilder
                .bind(orderAcceptedQueue())
                .to(kdgExchange())
                .with(properties.getOrderAcceptedBinding());
    }

    @Bean
    Binding orderRejectedBinding() {
        return BindingBuilder
                .bind(orderRejectedQueue())
                .to(kdgExchange())
                .with(properties.getOrderRejectedBinding());
    }

    @Bean
    Binding orderReadyBinding() {
        return BindingBuilder
                .bind(orderReadyQueue())
                .to(kdgExchange())
                .with(properties.getOrderReadyBinding());
    }

    @Bean
    Binding orderPickedUpBinding() {
        return BindingBuilder
                .bind(orderPickedUpQueue())
                .to(kdgExchange())
                .with(properties.getOrderPickedUpBinding());
    }

    @Bean
    Binding orderDeliveredBinding() {
        return BindingBuilder
                .bind(orderDeliveredQueue())
                .to(kdgExchange())
                .with(properties.getOrderDeliveredBinding());
    }
}
