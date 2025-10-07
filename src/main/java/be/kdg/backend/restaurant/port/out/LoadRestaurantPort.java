package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadRestaurantPort {
    Optional<Restaurant> loadByOwnerId(UUID ownerId);
    List<Restaurant> loadAll();
}
