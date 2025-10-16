package be.kdg.sa.orderservice.infrastructure.order.jpa;

import be.kdg.sa.orderservice.domain.order.OrderLine;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "OrderLines")
@Getter
public class JpaOrderLineEntity {
    @Id
    @Column
    private UUID dishId;

    @Column
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderId", nullable = false)
    private JpaOrderEntity order;

    protected JpaOrderLineEntity() {}

    public JpaOrderLineEntity(UUID dishId, int quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public static JpaOrderLineEntity fromDomain(OrderLine orderLine) {
        return new JpaOrderLineEntity(
                orderLine.getId().id(),
                orderLine.getQuantity()
        );
    }
}
