package be.kdg.sa.orderservice.domain.order;

public record Address(
        String street,
        int number,
        int postalCode,
        String city,
        String country
) {

}


