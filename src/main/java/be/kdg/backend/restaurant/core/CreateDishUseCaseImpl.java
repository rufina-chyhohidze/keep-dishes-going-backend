package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.port.in.CreateDishCommand;
import be.kdg.backend.restaurant.port.in.CreateDishUseCase;
import be.kdg.backend.restaurant.port.out.LoadMenuPort;
import be.kdg.backend.restaurant.port.out.SaveMenuPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class CreateDishUseCaseImpl implements CreateDishUseCase {
    private final LoadMenuPort loadMenuPort;
    private final SaveMenuPort saveMenuPort;

    public CreateDishUseCaseImpl(LoadMenuPort loadMenuPort, SaveMenuPort saveMenuPort) {
        this.loadMenuPort = loadMenuPort;
        this.saveMenuPort = saveMenuPort;
    }

    @Override
    public UUID createDish(CreateDishCommand command) {
        var menu = loadMenuPort.loadMenuByRestaurantId(command.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for restaurant " + command.restaurantId()));

        Dish dish = menu.addDish(
                command.name(),
                command.type(),
                command.foodTags(),
                command.description(),
                command.price(),
                command.pictureUrl()
        );

        saveMenuPort.save(menu);
        return dish.getDishId();
    }
}
