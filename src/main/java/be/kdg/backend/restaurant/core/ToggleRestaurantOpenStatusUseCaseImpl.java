package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Restaurant;
import be.kdg.backend.restaurant.port.in.ToggleRestaurantOpenStatusUseCase;
import be.kdg.backend.restaurant.port.out.UpdateRestaurantStatusPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ToggleRestaurantOpenStatusUseCaseImpl implements ToggleRestaurantOpenStatusUseCase {
    private final UpdateRestaurantStatusPort updateRestaurantStatusPort;

    public ToggleRestaurantOpenStatusUseCaseImpl(UpdateRestaurantStatusPort updateRestaurantStatusPort) {
        this.updateRestaurantStatusPort = updateRestaurantStatusPort;
    }

    @Override
    public Restaurant toggleOpenStatus(UUID restaurantId, UUID ownerId) {
        Restaurant restaurant = updateRestaurantStatusPort.loadById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new SecurityException("Not authorized to update this restaurant");
        }

        restaurant.toggleOpen();
        updateRestaurantStatusPort.saveStatus(restaurant);
        return restaurant;
    }

}
