package be.kdg.sa.orderservice.api.order.dtos;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record NewOrderDto(
        String restaurantId
) {}
