package be.kdg.sa.orderservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @AllArgsConstructor
@ConfigurationProperties(prefix = "spring.rabbitmq.kdg")
public class RabbitMQProperties {
    private String exchangeName;
    private String orderPlacedBinding;
    private String orderRejectedQueue;
    private String orderRejectedBinding;
    private String orderAcceptedOrderQueue;
    private String orderAcceptedOrderBinding;
    private String orderReadyOrderQueue;
    private String orderReadyOrderBinding;
    private String orderPickedUpQueue;
    private String orderPickedUpBinding;
    private String orderDeliveredQueue;
    private String orderDeliveredBinding;
}
