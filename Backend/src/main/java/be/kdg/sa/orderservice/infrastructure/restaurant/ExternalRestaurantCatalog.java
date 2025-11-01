package be.kdg.sa.orderservice.infrastructure.restaurant;

import be.kdg.sa.orderservice.domain.restaurant.dish.Dish;
import be.kdg.sa.orderservice.domain.restaurant.dish.DishId;
import be.kdg.sa.orderservice.domain.restaurant.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ExternalRestaurantCatalog implements RestaurantCatalog {

    private final RestClient restClient;

    public ExternalRestaurantCatalog(@Qualifier("restaurantCatalogApi") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override public Optional <List <Restaurant>> findAll() {
        log.info("Finding all restaurants");
        try {
            List <RestaurantResponse> response = restClient
                    .get()
                    .retrieve()
                    .body(new ParameterizedTypeReference<>(){});

            if (response == null) {
                log.error("No restaurants found");
                return Optional.empty();
            }

            return Optional.of(
                    response.stream()
                            .map(RestaurantResponse::toRestaurant)
                            .toList());
        } catch (final HttpStatusCodeException e) {
            log.error("Error while fetching restaurants", e);
            return Optional.empty();
        }
    }

    @Override public Optional <Restaurant> findByIdWithMenuAndOpeningHours(RestaurantId restaurantId) {
        log.info("Finding restaurant with id {}", restaurantId);
        try {
            final RestaurantResponse response = restClient
                    .get()
                    .uri("/" + restaurantId.id().toString())
                    .retrieve()
                    .body(RestaurantResponse.class);

            if (response == null) {
                log.error("No restaurant found with id {}", restaurantId);
                return Optional.empty();
            }

            return Optional.of(response.toRestaurant());
        } catch (final HttpStatusCodeException e) {
            log.error("Error while fetching restaurant with id {}", restaurantId, e);
            return Optional.empty();
        }
    }

    @Override public Optional< List <Dish>> findMenu(RestaurantId restaurantId) {
        log.info("Finding menu for restaurant with id {}", restaurantId);
        try {
            final List <DishResponse> response = restClient
                    .get()
                    .uri("/" + restaurantId.id().toString() + "/menu")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>(){});

            if (response == null) {
                log.error("No menu found for restaurant with id {}", restaurantId);
                return Optional.of(List.of());
            }

            return Optional.of(
                    response.stream()
                            .map(DishResponse::toDish)
                            .toList()
            );
        } catch (final HttpStatusCodeException e) {
            log.error("Error while fetching menu for restaurant with id {}", restaurantId, e);
            return Optional.empty();
        }
    }

    @Override public Optional <Dish> findDishById(RestaurantId restaurantId, DishId dishId) {
        log.info("Finding dish with id {} for restaurant with id {}", dishId, restaurantId);
        try {
            final DishResponse response = restClient
                    .get()
                    .uri("/" + restaurantId.id().toString() + "/menu/" + dishId.id().toString())
                    .retrieve()
                    .body(DishResponse.class);

            if (response == null) {
                log.error("No dish found with id {} for restaurant with id {}", dishId, restaurantId);
                return Optional.empty();
            }

            return Optional.of(response.toDish());
        } catch (final HttpStatusCodeException e) {
            log.error("Error while fetching dish with id {} for restaurant with id {}", dishId, restaurantId, e);
            return Optional.empty();
        }
    }
}
