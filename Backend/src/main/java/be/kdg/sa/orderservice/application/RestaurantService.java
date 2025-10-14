package be.kdg.sa.orderservice.application;

import be.kdg.sa.orderservice.domain.restaurant.Restaurant;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantCatalog;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantCatalog restaurants;

    public RestaurantService(RestaurantCatalog restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant> findAll() {
        return restaurants.findAll().orElse(List.of());
    }

    public Restaurant findByIdWithMenuAndOpeningHours(RestaurantId restaurantId) {
        return restaurants.findByIdWithMenuAndOpeningHours(restaurantId).orElseThrow(restaurantId::notFound);
    }
}
