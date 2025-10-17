package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.domain.Menu;
import be.kdg.backend.restaurant.port.in.UnpublishDishUseCase;
import be.kdg.backend.restaurant.port.out.LoadDishPort;
import be.kdg.backend.restaurant.port.out.LoadMenuPort;
import be.kdg.backend.restaurant.port.out.SaveDishPort;
import be.kdg.backend.restaurant.port.out.SaveMenuPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class UnpublishDishUseCaseImpl implements UnpublishDishUseCase {
    private final LoadMenuPort loadMenuPort;
    private final SaveMenuPort saveMenuPort;

    public UnpublishDishUseCaseImpl(LoadMenuPort loadMenuPort, SaveMenuPort saveMenuPort) {
        this.loadMenuPort = loadMenuPort;
        this.saveMenuPort = saveMenuPort;
    }

    @Override
    public void unpublishDish(UUID restaurantId, UUID dishId) {
        Menu menu = loadMenuPort.loadMenuByRestaurantId(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for restaurant: " + restaurantId));

        menu.unpublishDish(dishId);
        saveMenuPort.save(menu);
    }
}
