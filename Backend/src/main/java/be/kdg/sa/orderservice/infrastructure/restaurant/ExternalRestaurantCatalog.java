package be.kdg.sa.orderservice.infrastructure.restaurant;

import be.kdg.sa.orderservice.domain.dish.Dish;
import be.kdg.sa.orderservice.domain.dish.DishId;
import be.kdg.sa.orderservice.domain.restaurant.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Optional;

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
                    .uri("/" + restaurantId.id().toString() + "/menu/" + dishId.id().toString())
                    .retrieve()
                    .body(DishResponse.class);

            if (response == null) return Optional.empty();

            return Optional.of(response.toDish());
        } catch (final HttpStatusCodeException e) {
            return Optional.empty();
        }
    }
}
