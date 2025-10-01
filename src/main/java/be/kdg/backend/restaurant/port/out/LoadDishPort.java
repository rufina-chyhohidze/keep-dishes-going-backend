package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Dish;

import java.util.Optional;
import java.util.UUID;

public interface LoadDishPort {
    Optional<Dish> loadById(UUID dishId);
}
