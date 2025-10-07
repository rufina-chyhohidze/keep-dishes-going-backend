package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Menu;
import be.kdg.backend.restaurant.port.in.PublishDishUseCase;
import be.kdg.backend.restaurant.port.out.LoadMenuPort;
import be.kdg.backend.restaurant.port.out.SaveDishPort;
import be.kdg.backend.restaurant.port.out.SaveMenuPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class PublishDishUseCaseImpl implements PublishDishUseCase {
    private final LoadMenuPort loadMenuPort;
    private final SaveMenuPort saveMenuPort;

    public PublishDishUseCaseImpl(LoadMenuPort loadMenuPort,SaveMenuPort saveMenuPort) {
        this.loadMenuPort = loadMenuPort;
        this.saveMenuPort = saveMenuPort;
    }

    @Override
    public void publishDish(UUID restaurantId, UUID dishId) {
        Menu menu = loadMenuPort.loadMenuByRestaurantId(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for restaurant: " + restaurantId));

        menu.publishDish(dishId);

        saveMenuPort.save(menu);
    }

}
