package be.kdg.sa.orderservice.domain.dish;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Dish {
    private DishId id;
    private String name;
    private String description;
    private double price;
}
