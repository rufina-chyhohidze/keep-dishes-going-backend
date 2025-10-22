package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Restaurant;

import java.util.List;

public interface LoadRestaurantPort {
    List<Restaurant> loadAll();
}
