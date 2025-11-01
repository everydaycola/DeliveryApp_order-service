package be.kdg.sa.orderservice.infrastructure.restaurant;

import be.kdg.sa.orderservice.domain.restaurant.*;
import org.jmolecules.ddd.annotation.ValueObject;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@ValueObject
record RestaurantResponse(
        String id,
        String name,
        AdressResponse address,
        String contactEmail,
        String type,
        List <OpeningHour> openingHours,
        String logo,
        String isOpen,
        String priceCriteria
) {
    public Restaurant toRestaurant() {
        return new Restaurant(
                new RestaurantId(UUID.fromString(this.id)),
                this.name,
                new Address(this.address.street,
                            this.address.number,
                            this.address.postalCode,
                            this.address.country),
                this.contactEmail,
                RestaurantType.valueOf(this.type),
                this.openingHours
                        .stream()
                        .map(hours ->
                                     new RestaurantOpeningHours(
                                             DayOfWeek.valueOf(hours.day),
                                             LocalTime.parse(hours.openingTime),
                                             LocalTime.parse(hours.closingTime)
                                     )
                        )
                        .toList(),
                PriceCriteria.fromDescription(this.priceCriteria),
                this.logo,
                Boolean.parseBoolean(this.isOpen)
        );
    }

    private record AdressResponse(
            String street,
            int number,
            int postalCode,
            String country
    ) {}

    private record OpeningHour(
            String day,
            String openingTime,
            String closingTime
    ) {}
}
