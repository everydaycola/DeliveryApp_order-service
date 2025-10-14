package be.kdg.sa.orderservice.domain.customer;

import org.springframework.util.Assert;

import java.util.UUID;

public record CustomerId(UUID id) {
    public CustomerId {
        Assert.notNull(id, "id cannot be null");
    }

    public static CustomerId create() {
        return new CustomerId(UUID.randomUUID());
    }
}
