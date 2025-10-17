package be.kdg.backend.restaurant.port.in;

import be.kdg.backend.restaurant.domain.Dish;

import java.util.UUID;

public interface UnpublishDishUseCase {
    void unpublishDish(UUID restaurantId, UUID dishId);
}
