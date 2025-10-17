package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Dish;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadDishPort {
    Optional<Dish> loadById(UUID dishId);
    List<Dish> loadByRestaurantId(UUID restaurantId);
    Optional<Dish> loadByIdAndRestaurantId(UUID dishId, UUID restaurantId);
}
