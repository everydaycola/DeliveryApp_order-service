package be.kdg.sa.orderservice.domain.restaurant;

import be.kdg.sa.orderservice.domain.restaurant.dish.Dish;
import be.kdg.sa.orderservice.domain.restaurant.dish.DishId;

import java.util.List;
import java.util.Optional;

public interface RestaurantCatalog {
    Optional<List<Restaurant>> findAll();

    Optional<Restaurant> findByIdWithMenuAndOpeningHours(RestaurantId restaurantId);

    Optional<List<Dish>> findMenu(RestaurantId restaurantId);

    Optional<Dish> findDishById(RestaurantId restaurantId, DishId dishId);
}
