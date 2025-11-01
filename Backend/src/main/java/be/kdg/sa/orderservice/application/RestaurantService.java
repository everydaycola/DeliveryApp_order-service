package be.kdg.sa.orderservice.application;

import be.kdg.sa.orderservice.domain.restaurant.Restaurant;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantCatalog;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import be.kdg.sa.orderservice.domain.restaurant.dish.Dish;
import be.kdg.sa.orderservice.domain.restaurant.dish.DishId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RestaurantService {
    private final RestaurantCatalog restaurants;

    public RestaurantService(RestaurantCatalog restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant> findAll() {
        log.info("Finding all restaurants");
        return restaurants.findAll().orElse(List.of());
    }

    public Restaurant findByIdWithMenuAndOpeningHours(RestaurantId restaurantId) {
        log.info("Finding restaurant with id {}", restaurantId);
        return restaurants.findByIdWithMenuAndOpeningHours(restaurantId).orElseThrow(restaurantId::notFound);
    }

    public List<Dish> findMenu(RestaurantId restaurantId) {
        log.info("Finding menu for restaurant with id {}", restaurantId);
        return restaurants.findMenu(restaurantId).orElse(List.of());
    }

    public Dish findDishById(RestaurantId restaurantId, DishId dishId) {
        log.info("Finding dish with id {} for restaurant with id {}", dishId, restaurantId);
        return restaurants.findDishById(restaurantId, dishId).orElseThrow(dishId::notFound);
    }
}
