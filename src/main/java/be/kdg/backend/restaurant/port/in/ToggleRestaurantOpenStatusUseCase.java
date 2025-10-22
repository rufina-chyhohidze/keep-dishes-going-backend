package be.kdg.backend.restaurant.port.in;

import be.kdg.backend.restaurant.domain.Restaurant;

import java.util.UUID;

public interface ToggleRestaurantOpenStatusUseCase {
    Restaurant toggleOpenStatus(UUID restaurantId, UUID ownerId);
}
