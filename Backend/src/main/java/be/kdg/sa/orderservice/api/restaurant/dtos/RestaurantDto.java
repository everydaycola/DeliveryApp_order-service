package be.kdg.sa.orderservice.api.restaurant.dtos;

import be.kdg.sa.orderservice.domain.restaurant.PriceCriteria;
import be.kdg.sa.orderservice.domain.restaurant.Restaurant;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantType;


import java.util.List;
import java.util.UUID;

public record RestaurantDto(
        UUID id,
        String name,
        AddressDto address,
        String contactEmail,
        RestaurantType type,
        List<RestaurantOpeningHoursDto> openingHours,
        String logo,
        boolean isOpen,
        PriceCriteria priceCriteria) {
    public static RestaurantDto from(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId().id(),
                restaurant.getName(),
                AddressDto.from(restaurant.getAddress()),
                restaurant.getContactEmail(),
                restaurant.getType(),
                restaurant.getOpeningHours().stream().map(RestaurantOpeningHoursDto::from).toList(),
                restaurant.getLogo(),
                restaurant.isOpen(),
                restaurant.getPriceCriteria()
        );
    }
}
