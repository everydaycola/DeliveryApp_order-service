package be.kdg.sa.orderservice.domain.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class RestaurantOpeningHours {
    private DayOfWeek day;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
