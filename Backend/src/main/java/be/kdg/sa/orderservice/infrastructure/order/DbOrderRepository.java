package be.kdg.sa.orderservice.infrastructure.order;

import be.kdg.sa.orderservice.domain.order.Order;
import be.kdg.sa.orderservice.domain.order.OrderId;
import be.kdg.sa.orderservice.domain.order.OrderRepository;
import be.kdg.sa.orderservice.infrastructure.order.jpa.JpaOrderEntity;
import be.kdg.sa.orderservice.infrastructure.order.jpa.JpaOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class DbOrderRepository implements OrderRepository {

    public final JpaOrderRepository jpaOrderRepository;

    public DbOrderRepository(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        log.info("Finding order with id={}", orderId.id());
        return this.jpaOrderRepository.findById(orderId.id())
                .map(JpaOrderEntity::toDomain);
    }

    @Override public void save(Order order) {
        log.info("saving order: {}", order.getOrderId().id());
        final var jpaOrderEntity = JpaOrderEntity.fromDomain(order);
        this.jpaOrderRepository.save(jpaOrderEntity);
    }
}
