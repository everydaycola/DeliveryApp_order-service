package be.kdg.sa.orderservice.api;

import be.kdg.sa.orderservice.application.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Cart")
public class ShoppingCartController {
    private final OrderService orders;

    public ShoppingCartController(OrderService orders) {
        this.orders = orders;
    }
}
