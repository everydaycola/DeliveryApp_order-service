package be.kdg.sa.orderservice.infrastructure.jpa;

import be.kdg.sa.orderservice.domain.order.OrderId;
import be.kdg.sa.orderservice.domain.order.OrderLine;
import be.kdg.sa.orderservice.domain.order.OrderStatus;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity, UUID> {

}
