package be.kdg.sa.orderservice.api.restaurant;

import be.kdg.sa.orderservice.api.restaurant.dtos.DishDto;
import be.kdg.sa.orderservice.api.restaurant.dtos.RestaurantDto;
import be.kdg.sa.orderservice.application.RestaurantService;
import be.kdg.sa.orderservice.domain.dish.Dish;
import be.kdg.sa.orderservice.domain.dish.DishId;
import be.kdg.sa.orderservice.domain.restaurant.Restaurant;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        final RestaurantId restaurantId = new RestaurantId(id);
        final Restaurant restaurant = restaurants.findByIdWithMenuAndOpeningHours(restaurantId);
        return ResponseEntity.ok(RestaurantDto.from(restaurant));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> findAll() {
        List<Restaurant> allRestaurants = restaurants.findAll();

        List <RestaurantDto> dtos = allRestaurants.stream()
                                                  .map(RestaurantDto::from)
                                                  .toList();

        return ResponseEntity.ok(dtos);
    }

    //Dishes
    @GetMapping("/{id}/menu")
    public ResponseEntity<List<DishDto>> findMenu(@PathVariable final UUID id) {
        final RestaurantId restaurantId = new RestaurantId(id);
        List<Dish> allDishes = restaurants.findMenu(restaurantId);

        List<DishDto> dtos = allDishes.stream()
                                      .map(DishDto::from)
                                      .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/menu/{dishId}")
    public ResponseEntity<DishDto> findDish(@PathVariable final UUID id, @PathVariable final UUID dishId) {
        final RestaurantId restaurantId = new RestaurantId(id);
        final DishId dId = new DishId(dishId);

        Dish dish = restaurants.findDishById(restaurantId, dId);

        return ResponseEntity.ok(DishDto.from(dish));
    }
}
