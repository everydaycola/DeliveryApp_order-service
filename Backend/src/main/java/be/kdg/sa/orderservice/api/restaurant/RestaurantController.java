package be.kdg.sa.orderservice.api.restaurant;

import be.kdg.sa.orderservice.api.restaurant.dtos.RestaurantDto;
import be.kdg.sa.orderservice.application.RestaurantService;
import be.kdg.sa.orderservice.domain.restaurant.Restaurant;
import be.kdg.sa.orderservice.domain.restaurant.RestaurantId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
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

//    @GetMapping
//    public ResponseEntity<List<RestaurantDto>> findAll() {
//        List<Restaurant> allRestaurants = restaurants.findAll();
//
//        List<RestaurantDto> dtos = allRestaurants.stream()
//                .map(RestaurantDto::from)
//                .toList();
//
//        return ResponseEntity.ok(dtos);
//    }
}
