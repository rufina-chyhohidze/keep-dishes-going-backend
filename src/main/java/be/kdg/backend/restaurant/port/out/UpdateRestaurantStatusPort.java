package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Restaurant;

import java.util.Optional;
import java.util.UUID;

public interface UpdateRestaurantStatusPort {
    Optional<Restaurant> loadById(UUID restaurantId);
    void saveStatus(Restaurant restaurant);
}
