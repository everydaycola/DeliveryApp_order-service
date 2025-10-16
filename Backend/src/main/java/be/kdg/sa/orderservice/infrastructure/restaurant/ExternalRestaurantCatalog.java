package be.kdg.sa.orderservice.infrastructure.restaurant;

import be.kdg.sa.orderservice.domain.dish.Dish;
import be.kdg.sa.orderservice.domain.dish.DishId;
import be.kdg.sa.orderservice.domain.restaurant.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.core.ParameterizedTypeReference;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component public class ExternalRestaurantCatalog implements RestaurantCatalog {

    private final RestClient restClient;

    public ExternalRestaurantCatalog(@Qualifier("restaurantCatalogApi") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override public Optional <List <Restaurant>> findAll() {
        try {
            List <RestaurantResponse> response = restClient
                    .get()
                    .retrieve()
                    .body(new ParameterizedTypeReference<>(){});

            if (response == null) return Optional.empty();

            return Optional.of(
                    response.stream()
                            .map(RestaurantResponse::toRestaurant)
                            .toList());
        } catch (final HttpStatusCodeException e) {
            return Optional.empty();
        }
    }

    @Override public Optional <Restaurant> findByIdWithMenuAndOpeningHours(RestaurantId restaurantId) {
        try {
            final RestaurantResponse response = restClient
                    .get()
                    .uri("/" + restaurantId.id().toString())
                    .retrieve()
                    .body(RestaurantResponse.class);

            if (response == null) return Optional.empty();

            return Optional.of(response.toRestaurant());
        } catch (final HttpStatusCodeException e) {
            return Optional.empty();
        }
    }

    @Override public Optional< List <Dish>> findMenu(RestaurantId restaurantId) {
        try {
            final List <DishResponse> response = restClient
                    .get()
                    .uri("/" + restaurantId.id().toString() + "/menu")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>(){});

            if (response == null) return Optional.of(List.of());

            return Optional.of(
                    response.stream()
                            .map(DishResponse::toDish)
                            .toList()
            );
        } catch (final HttpStatusCodeException e) {
            return Optional.empty();
        }
    }

    @Override public Optional <Dish> findDishById(RestaurantId restaurantId, DishId dishId) {
        try {
            final DishResponse response = restClient
                    .get()
                    .uri("/" + restaurantId.id().toString() + "/menu" + restaurantId.id().toString())
                    .retrieve()
                    .body(DishResponse.class);

            if (response == null) return Optional.empty();

            return Optional.of(response.toDish());
        } catch (final HttpStatusCodeException e) {
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
        private Restaurant toRestaurant() {
            return new Restaurant(
                    new RestaurantId(UUID.fromString( this.id)),
                    this.name,
                    new Address( this.address.street,
                                 this.address.number,
                                 this.address.postalCode,
                                 this.address.country),
                    this.contactEmail,
                    RestaurantType.valueOf( this.type),
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
                    PriceCriteria.valueOf(this.priceCriteria),
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

    private record DishResponse(
            String id,
            String name,
            String description,
            double price
    ) {
        private Dish toDish() {
            return new Dish(
                    new DishId(UUID.fromString(this.id)),
                    this.name,
                    this.description,
                    this.price
            );
        }
    }
}
