package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.domain.DishAvailability;
import be.kdg.backend.restaurant.port.in.LoadDishesByRestaurantUseCase;
import be.kdg.backend.restaurant.port.out.LoadDishPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LoadDishesByRestaurantUseCaseImpl implements LoadDishesByRestaurantUseCase {
    private final LoadDishPort loadDishPort;

    public LoadDishesByRestaurantUseCaseImpl(LoadDishPort loadDishPort) {
        this.loadDishPort = loadDishPort;
    }

    @Override
    public List<Dish> loadByRestaurantId(UUID restaurantId) {
        return loadDishPort.loadByRestaurantId(restaurantId)
                .stream()
                .filter(d -> d.getAvailability() == DishAvailability.PUBLISHED)
                .toList();
    }

}
