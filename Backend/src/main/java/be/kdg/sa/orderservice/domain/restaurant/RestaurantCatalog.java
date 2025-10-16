package be.kdg.sa.orderservice.domain.restaurant;

import be.kdg.sa.orderservice.domain.dish.Dish;

import java.util.List;
import java.util.Optional;

public interface RestaurantCatalog {
    Optional<List<Restaurant>> findAll();

    Optional<Restaurant> findByIdWithMenuAndOpeningHours(RestaurantId restaurantId);

    Optional<List<Dish>> findMenu(RestaurantId restaurantId);
}
