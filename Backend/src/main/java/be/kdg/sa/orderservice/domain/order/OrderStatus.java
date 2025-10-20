package be.kdg.sa.orderservice.domain.order;

public enum OrderStatus {
    UNCONFIRMED,
    PENDING,
    ACCEPTED,
    DECLINED,
    IN_PREPARATION,
    READY,
    IN_DELIVERY,
    DELIVERED
}
