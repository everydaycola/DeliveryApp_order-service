package be.kdg.sa.orderservice.domain.order;

import be.kdg.sa.orderservice.domain.NotFoundException;

import java.util.UUID;

public record OrderLineId(UUID id) {
    public static OrderLineId create() {
        return new OrderLineId(UUID.randomUUID());
    }

    public NotFoundException notFound() {
        return new NotFoundException("OrderLine [" + id + "] not found");
    }}
