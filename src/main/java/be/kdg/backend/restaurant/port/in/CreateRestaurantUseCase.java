package be.kdg.backend.restaurant.port.in;

import be.kdg.backend.restaurant.port.in.request.CreateRestaurantCommand;

import java.util.UUID;

public interface CreateRestaurantUseCase {
    UUID createRestaurant(CreateRestaurantCommand command);
}
