package be.kdg.sa.orderservice.infrastructure.restaurant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ExternalRestaurantCatalogConfig {
    @Bean("restaurantCatalogApi")
    RestClient productCatalogRestTemplate(@Value("${restaurant-catalog-api.url}") final String url) {
        return RestClient.create(url);
    }
}
