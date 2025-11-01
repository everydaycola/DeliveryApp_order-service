package be.kdg.sa.orderservice.api.restaurant.dtos;


import be.kdg.sa.orderservice.domain.restaurant.RestaurantOpeningHours;
import org.jmolecules.ddd.annotation.ValueObject;

import java.time.DayOfWeek;
import java.time.LocalTime;

@ValueObject
public record RestaurantOpeningHoursDto(DayOfWeek day, LocalTime openingTime, LocalTime closingTime) {
    public static RestaurantOpeningHoursDto from(RestaurantOpeningHours openingHours){
        return new RestaurantOpeningHoursDto(openingHours.day(),openingHours.openingTime(),openingHours.closingTime());
    }
}
