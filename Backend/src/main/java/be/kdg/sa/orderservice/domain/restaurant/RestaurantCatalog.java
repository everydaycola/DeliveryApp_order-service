package be.kdg.sa.orderservice.domain.restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantCatalog {
    Optional<List<Restaurant>> findAll();

    Optional<Restaurant> findByIdWithMenuAndOpeningHours(RestaurantId restaurantId);
}
