package be.kdg.backend.restaurant.port.in;

import be.kdg.backend.restaurant.domain.Dish;

import java.util.List;
import java.util.UUID;

public interface LoadDishesByRestaurantUseCase {
    List<Dish> loadByRestaurantId(UUID restaurantId);
}
