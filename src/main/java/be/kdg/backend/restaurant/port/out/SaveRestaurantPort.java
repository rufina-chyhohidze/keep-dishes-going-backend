package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Restaurant;

public interface SaveRestaurantPort {
    Restaurant save(Restaurant restaurant);
}
