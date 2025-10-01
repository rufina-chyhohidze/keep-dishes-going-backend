package be.kdg.backend.restaurant.adapter.out;

import be.kdg.backend.restaurant.domain.Restaurant;
import be.kdg.backend.restaurant.port.out.LoadRestaurantPort;
import be.kdg.backend.restaurant.port.out.SaveRestaurantPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryRestaurantRepository implements LoadRestaurantPort, SaveRestaurantPort {
    private final Map<UUID, Restaurant> storage = new HashMap<>();

    @Override
    public Optional<Restaurant> loadByOwnerId(UUID ownerId) {
        return storage.values().stream()
                .filter(r -> r.getOwnerId().equals(ownerId))
                .findFirst();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        storage.put(restaurant.getRestaurantId(), restaurant);
        return restaurant;
    }
}
