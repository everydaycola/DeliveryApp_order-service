package be.kdg.sa.orderservice.domain.dish;


import lombok.Getter;

@Getter
public class Dish {
    private DishId id;
    private String name;
    private DishState state;
    private String description;
    private double price;
}
