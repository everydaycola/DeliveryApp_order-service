package be.kdg.sa.orderservice.infrastructure.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {

    public static final String KDG_EXCHANGE_NAME = "kdg_exchange";

    public static final String ORDER_ACCEPTED_QUEUE_NAME = "order_accepted_queue";
    public static final String ORDER_REJECTED_QUEUE_NAME = "order_rejected_queue";
    public static final String ORDER_READY_QUEUE_NAME = "order_ready_queue";
    public static final String ORDER_PICKED_UP_QUEUE_NAME = "order_picked_up_queue";
    public static final String ORDER_DELIVERED_QUEUE_NAME = "order_delivered_queue";


    @Bean
    TopicExchange kdgExchange() {
        return new TopicExchange(KDG_EXCHANGE_NAME);
    }

    @Bean
    Queue orderAcceptedQueue() {
        return QueueBuilder.nonDurable(ORDER_ACCEPTED_QUEUE_NAME).build();
    }

    @Bean
    Queue orderRejectedQueue() {
        return QueueBuilder.nonDurable(ORDER_REJECTED_QUEUE_NAME).build();
    }

    @Bean
    Queue orderReadyQueue() {
        return QueueBuilder.nonDurable(ORDER_READY_QUEUE_NAME).build();
    }

    @Bean
    Queue orderPickedUpQueue() {
        return QueueBuilder.nonDurable(ORDER_PICKED_UP_QUEUE_NAME).build();
    }

    @Bean
    Queue orderDeliveredQueue() {
        return QueueBuilder.nonDurable(ORDER_DELIVERED_QUEUE_NAME).build();
    }

    @Bean
    Binding orderAcceptedBinding() {
        return BindingBuilder.bind(orderAcceptedQueue()).to(kdgExchange()).with("order.accepted.#");
    }

    @Bean
    Binding orderRejectedBinding() {
        return BindingBuilder.bind(orderRejectedQueue()).to(kdgExchange()).with("order.rejected");
    }

    @Bean
    Binding orderReadyBinding() {
        return BindingBuilder.bind(orderReadyQueue()).to(kdgExchange()).with("order.ready");
    }

    @Bean
    Binding orderPickedUpBinding() {
        return BindingBuilder.bind(orderPickedUpQueue()).to(kdgExchange()).with("order.pickedUp");
    }

    @Bean
    Binding orderDeliveredBinding() {
        return BindingBuilder.bind(orderDeliveredQueue()).to(kdgExchange()).with("order.delivered");
    }
}
