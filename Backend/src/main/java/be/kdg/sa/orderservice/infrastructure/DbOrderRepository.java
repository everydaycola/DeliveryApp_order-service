package be.kdg.sa.orderservice.infrastructure;

import be.kdg.sa.orderservice.domain.order.Order;
import be.kdg.sa.orderservice.domain.order.OrderId;
import be.kdg.sa.orderservice.domain.order.OrderRepository;
import be.kdg.sa.orderservice.infrastructure.jpa.JpaOrderEntity;
import be.kdg.sa.orderservice.infrastructure.jpa.JpaOrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DbOrderRepository implements OrderRepository {

    public final JpaOrderRepository jpaOrderRepository;

    public DbOrderRepository(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return this.jpaOrderRepository.findById(orderId.id())
                .map(JpaOrderEntity::toDomain);
    }
}
