package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Menu;

import java.util.Optional;
import java.util.UUID;

public interface LoadMenuPort {
    Optional<Menu> loadMenuByRestaurantId(UUID restaurantId);
}
