package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.domain.DishAvailability;
import be.kdg.backend.restaurant.port.in.LoadDishesByRestaurantUseCase;
import be.kdg.backend.restaurant.port.out.LoadDishPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LoadDishesByRestaurantUseCaseImpl implements LoadDishesByRestaurantUseCase {
    private final LoadDishPort loadDishPort;

    public LoadDishesByRestaurantUseCaseImpl(LoadDishPort loadDishPort) {
        this.loadDishPort = loadDishPort;
    }

    /**
     * For customer, to see only published dishes
     * @param restaurantId
     * @return
     */
    @Override
    public List<Dish> loadByRestaurantId(UUID restaurantId) {
        return loadDishPort.loadByRestaurantId(restaurantId)
                .stream()
                .filter(d -> d.getAvailability() == DishAvailability.PUBLISHED)
                .toList();
    }

    /**
     * For owner, to see all dishes he created at all states
     * @param restaurantId
     * @return
     */
    @Override
    public List<Dish> loadAllByRestaurantId(UUID restaurantId) {
        return loadDishPort.loadByRestaurantId(restaurantId);
    }

}
