package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.port.in.EditDishUseCase;
import be.kdg.backend.restaurant.port.in.request.EditDishCommand;
import be.kdg.backend.restaurant.port.out.LoadDishPort;
import be.kdg.backend.restaurant.port.out.SaveDishPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EditDishUseCaseImpl implements EditDishUseCase{
    private static final Logger log = LoggerFactory.getLogger(EditDishUseCaseImpl.class);

    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;

    public EditDishUseCaseImpl(LoadDishPort loadDishPort, SaveDishPort saveDishPort) {
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
    }

    @Override
    public UUID editDish(EditDishCommand command) {
        Dish dish = loadDishPort.loadById(command.dishId())
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + command.dishId()));


        if (!dish.getRestaurantId().equals(command.restaurantId())) {
            throw new IllegalArgumentException("Dish does not belong to restaurant " + command.restaurantId());
        }

        dish.editDraft(
                command.name(),
                command.type(),
                command.foodTags(),
                command.description(),
                command.price(),
                command.pictureUrl()
        );

        saveDishPort.save(dish);
        log.info("Edited draft dish {} for restaurant {}", dish.getDishId(), dish.getRestaurantId());
        return dish.getDishId();
    }
}
