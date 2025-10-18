package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.port.in.UpdateStockStatusUseCase;
import be.kdg.backend.restaurant.port.out.LoadDishPort;
import be.kdg.backend.restaurant.port.out.SaveDishPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class UpdateStockStatusImpl implements UpdateStockStatusUseCase {

    private final LoadDishPort loadDishPort;
    private final SaveDishPort saveDishPort;

    public UpdateStockStatusImpl(LoadDishPort loadDishPort, SaveDishPort saveDishPort) {
        this.loadDishPort = loadDishPort;
        this.saveDishPort = saveDishPort;
    }


    @Override
    public void markOutOfStock(UUID dishId, UUID restaurantId) {
        Dish dish = loadDishPort.loadByIdAndRestaurantId(dishId,restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found"));

        dish.markOutOfStock();
        saveDishPort.save(dish);
    }

    @Override
    public void markInStock(UUID dishId, UUID restaurantId) {
        Dish dish = loadDishPort.loadByIdAndRestaurantId(dishId,restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Dish not found"));

        dish.markInStock();
        saveDishPort.save(dish);
    }

}
