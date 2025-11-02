package be.kdg.sa.orderservice.infrastructure.order.jpa;

import be.kdg.sa.orderservice.domain.order.*;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import be.kdg.sa.orderservice.domain.restaurant.dish.DishId;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity @Table(name = "Orders") public class JpaOrderEntity {
    @Id @Column private UUID orderId;

    @Column private OrderStatus status;

    @Setter @Column @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    private List <JpaOrderLineEntity> orderLines;

    @Column private UUID restaurantId;

    @Column private String comment;
    @Column private String name;
    @Column private String address;
    @Column private String contactEmail;

    protected JpaOrderEntity() {}

    public JpaOrderEntity(UUID orderId, OrderStatus status, UUID restaurantId, String comment, String name, String address, String contactEmail) {
        this.orderId = orderId;
        this.status = status;
        this.orderLines = List.of();
        this.restaurantId = restaurantId;
        this.comment = comment;
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
    }

    public static JpaOrderEntity fromDomain(Order order) {

        final var contactInfo = order.getContactInfo();
        final var jpaOrderEntity = new JpaOrderEntity(
                order.getOrderId().id(),
                order.getStatus(),
                order.getRestaurantId().id(),
                order.getComment(),
                contactInfo != null ? contactInfo.name() : null,
                contactInfo != null ? contactInfo.address() : null,
                contactInfo != null ? contactInfo.contactEmail() : null
        );

        final var jpaOrderEntities =
                order.getOrderLines()
                     .stream()
                     .map(JpaOrderLineEntity::fromDomain)
                     .toList();
        jpaOrderEntities.forEach(joe -> joe.setOrder(jpaOrderEntity));
        jpaOrderEntity.setOrderLines(jpaOrderEntities);

        return jpaOrderEntity;
    }

    public Order toDomain() {
        OrderContactInfo contactInfo ;
        if (this.name != null || this.address != null || this.contactEmail != null) {
            contactInfo = new OrderContactInfo(this.name, this.address, this.contactEmail);
        } else {
            contactInfo = OrderContactInfo.EMPTY;
        }

        return new Order(
                new OrderId(this.orderId),
                this.status,
                this.orderLines.stream()
                               .map(jpaOrderLine ->
                                            new OrderLine(
                                                    new OrderLineId(this.orderId),
                                                    jpaOrderLine.getQuantity(),
                                                    new DishId(jpaOrderLine.getDishId())
                                            ))
                               .toList(),
                new RestaurantId(this.restaurantId),
                this.comment,
                contactInfo
        );
    }

}
