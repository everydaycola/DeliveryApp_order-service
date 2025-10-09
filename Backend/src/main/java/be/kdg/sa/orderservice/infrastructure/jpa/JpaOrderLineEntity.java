package be.kdg.sa.orderservice.infrastructure.jpa;

import be.kdg.sa.orderservice.domain.order.OrderLine;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "OrderLine")
public class JpaOrderLineEntity {
    @Id
    @Column
    private UUID id;

    @Column
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private JpaOrderEntity Order;

    protected JpaOrderLineEntity() {}

    public JpaOrderLineEntity(UUID id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public static JpaOrderLineEntity fromDomain(OrderLine orderLine) {
        return new JpaOrderLineEntity(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }

    public UUID getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public JpaOrderEntity getOrder() {
        return Order;
    }
}
