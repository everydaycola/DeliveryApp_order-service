package be.kdg.sa.orderservice.api.order.dtos;

import be.kdg.sa.orderservice.domain.order.OrderContactInfo;

public record SubmittedOrderDto(
        String name,
        String address,
        String contactEmail
) {
    public OrderContactInfo toDomain() {
        return new OrderContactInfo(
                this.name,
                this.address,
                this.contactEmail
        );
    }

    public static SubmittedOrderDto from(final OrderContactInfo orderContactInfo) {
        return new SubmittedOrderDto(
                orderContactInfo.name(),
                orderContactInfo.address(),
                orderContactInfo.contactEmail()
        );
    }
}
