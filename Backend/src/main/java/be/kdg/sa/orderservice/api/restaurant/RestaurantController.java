package be.kdg.sa.orderservice.api.restaurant;

import be.kdg.sa.orderservice.api.restaurant.dtos.DishDto;
import be.kdg.sa.orderservice.api.restaurant.dtos.RestaurantDto;
import be.kdg.sa.orderservice.application.RestaurantService;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import be.kdg.sa.orderservice.domain.restaurant.dish.DishId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurants;

    public RestaurantController(RestaurantService restaurants) {
        this.restaurants = restaurants;
    }

    //GET
    //Restaurant
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> findById(@PathVariable final UUID id) {
        log.info("Finding restaurant with id {}", id);
        final var restaurantId = new RestaurantId(id);
        final var restaurant = restaurants.findByIdWithMenuAndOpeningHours(restaurantId);
        return ResponseEntity.ok(RestaurantDto.from(restaurant));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> findAll() {
        log.info("Finding all restaurants");
        final var allRestaurants = restaurants.findAll();

        final var dtos = allRestaurants.stream()
                .map(RestaurantDto::from)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    //Dishes
    @GetMapping("/{id}/menu")
    public ResponseEntity<List<DishDto>> findMenu(@PathVariable final UUID id) {
        log.info("Finding menu for restaurant with id {}", id);
        final var restaurantId = new RestaurantId(id);
        final var allDishes = restaurants.findMenu(restaurantId);

        final var dtos = allDishes.stream()
                .map(DishDto::from)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/menu/{dishId}")
    public ResponseEntity<DishDto> findDish(@PathVariable final UUID id, @PathVariable final UUID dishId) {
        log.info("Finding dish with id {} for restaurant with id {}", dishId, id);
        final var restaurantId = new RestaurantId(id);
        final var dId = new DishId(dishId);

        final var dish = restaurants.findDishById(restaurantId, dId);

        return ResponseEntity.ok(DishDto.from(dish));
    }
}
