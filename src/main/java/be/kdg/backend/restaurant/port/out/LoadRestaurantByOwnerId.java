package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Restaurant;

import java.util.Optional;
import java.util.UUID;

public interface LoadRestaurantByOwnerId {
    Optional<Restaurant> loadByOwnerId(UUID ownerId);

}
