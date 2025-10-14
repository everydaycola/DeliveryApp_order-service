package be.kdg.sa.orderservice.infrastructure.restaurant;

import be.kdg.sa.orderservice.domain.restaurant.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ExternalRestaurantCatalog implements RestaurantCatalog {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalRestaurantCatalog.class);

    private final RestClient restClient;

    public ExternalRestaurantCatalog(@Qualifier("restaurantCatalogApi") RestClient restClient) {
        this.restClient = restClient;
    }


    @Override
    public Optional<List<Restaurant>> findAll() {
        return Optional.of(List.of());
    }

    @Override
    public Optional<Restaurant> findByIdWithMenuAndOpeningHours(RestaurantId restaurantId) {
        try {
            final RestaurantResponse response = restClient
                    .get()
                    .uri("/" + restaurantId.id().toString())
                    .retrieve()
                    .body(RestaurantResponse.class);

            if (response == null) return Optional.empty();


            return Optional.of(
                    new Restaurant(
                            new RestaurantId(UUID.fromString(response.id)),
                            response.name,
                            new Address(
                                    response.address.street,
                                    response.address.number,
                                    response.address.postalCode,
                                    response.address.country
                            ),
                            response.contactEmail,
                            RestaurantType.valueOf(response.type),
                            response.openingHours.stream().map(hours ->
                                    new RestaurantOpeningHours(
                                            DayOfWeek.valueOf(hours.day),
                                            LocalTime.parse(hours.openingTime),
                                            LocalTime.parse(hours.closingTime)
                                    )
                            ).toList(),
                            PriceCriteria.valueOf(response.priceCriteria),
                            response.logo,
                            Boolean.parseBoolean(response.isOpen)
                    )
            );
        } catch (final HttpStatusCodeException e) {
            LOGGER.warn("Could not retrieve restaurant {}", restaurantId, e);
            return Optional.empty();
        }
    }

    private record RestaurantResponse(
            String id,
            String name,
            AdressResponse address,
            String contactEmail,
            String type,
            List<OpeningHour> openingHours,
            String logo,
            String isOpen,
            String priceCriteria

    ) {
    }

    private record AdressResponse(
            String street,
            int number,
            int postalCode,
            String country
    ) {
    }

    private record OpeningHour(
            String day,
            String openingTime,
            String closingTime
    ) {
    }
}
