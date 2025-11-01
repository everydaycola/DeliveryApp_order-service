package be.kdg.sa.orderservice.domain.restaurant;

import be.kdg.sa.orderservice.domain.restaurant.dish.Dish;
import org.jmolecules.ddd.annotation.AggregateRoot;

import java.util.List;

@AggregateRoot
public record Restaurant (
    RestaurantId id,
    String name,
    Address address,
    String contactEmail,
    RestaurantType type,
    List<RestaurantOpeningHours> openingHours,
    PriceCriteria priceCriteria,
    List<Dish> menu,
    String logo,
    boolean isOpen
) {
    public Restaurant(RestaurantId id, String name, Address address, String contactEmail, RestaurantType type, List<RestaurantOpeningHours> openingHours, PriceCriteria priceCriteria, String logo, boolean isOpen) {
        this(id, name, address, contactEmail, type, openingHours, priceCriteria, List.of(), logo, isOpen);
    }
}
