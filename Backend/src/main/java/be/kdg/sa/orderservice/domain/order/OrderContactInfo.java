package be.kdg.sa.orderservice.domain.order;

public record OrderContactInfo(
        String name,
        String address,
        String contactEmail
) {
    public static final OrderContactInfo EMPTY = new OrderContactInfo(null, null, null);

}
