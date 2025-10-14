package be.kdg.sa.orderservice.domain.restaurant;

import be.kdg.sa.orderservice.domain.dish.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.annotation.AggregateRoot;

import java.util.List;

@AggregateRoot
@Slf4j
@AllArgsConstructor
@Getter
public class Restaurant {
    private RestaurantId id;
    private String name;
    private Address address;
    private String contactEmail;
    private RestaurantType type;
    private List<RestaurantOpeningHours> openingHours;
    private PriceCriteria priceCriteria;
    private List<Dish> menu;
    private String logo;
    private boolean isOpen;

    public Restaurant(RestaurantId id, String name, Address address, String contactEmail, RestaurantType type, List<RestaurantOpeningHours> openingHours, PriceCriteria priceCriteria, String logo, boolean isOpen) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
        this.type = type;
        this.openingHours = openingHours;
        this.priceCriteria = priceCriteria;
        this.logo = logo;
        this.isOpen = isOpen;
    }
}
