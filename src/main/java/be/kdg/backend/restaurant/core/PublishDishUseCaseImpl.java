package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Menu;
import be.kdg.backend.restaurant.port.in.PublishDishUseCase;
import be.kdg.backend.restaurant.port.out.LoadMenuPort;
import be.kdg.backend.restaurant.port.out.SaveDishPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class PublishDishUseCaseImpl implements PublishDishUseCase {
    private final LoadMenuPort loadMenuPort;
    private final SaveDishPort saveDishPort;

    public PublishDishUseCaseImpl(LoadMenuPort loadMenuPort, SaveDishPort saveDishPort) {
        this.loadMenuPort = loadMenuPort;
        this.saveDishPort = saveDishPort;
    }

    @Override
    public void publishDish(UUID restaurantId, UUID dishId) {
        Menu menu = loadMenuPort.loadMenuByRestaurantId(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for restaurant " + restaurantId));

        menu.publishDish(dishId);

        menu.getDishes().stream()
                .filter(d -> d.getDishId().equals(dishId))
                .findFirst()
                .ifPresent(saveDishPort::save);
    }

}
