package be.kdg.sa.orderservice.api.restaurant.dtos;

import be.kdg.sa.orderservice.domain.restaurant.Restaurant;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantType;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.List;
import java.util.UUID;

@ValueObject
public record RestaurantDto(
        UUID id,
        String name,
        AddressDto address,
        String contactEmail,
        RestaurantType type,
        List<RestaurantOpeningHoursDto> openingHours,
        String logo,
        boolean isOpen,
        String priceCriteria) {
    public static RestaurantDto from(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.id().id(),
                restaurant.name(),
                AddressDto.from(restaurant.address()),
                restaurant.contactEmail(),
                restaurant.type(),
                restaurant.openingHours().stream().map(RestaurantOpeningHoursDto::from).toList(),
                restaurant.logo(),
                restaurant.isOpen(),
                restaurant.priceCriteria().getDescription()
        );
    }
}
