package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.RestaurantWorkload;

import java.util.Optional;
import java.util.UUID;

public interface LoadRestaurantWorkloadPort {
    Optional<RestaurantWorkload> loadByRestaurantId(UUID restaurantId);

}
