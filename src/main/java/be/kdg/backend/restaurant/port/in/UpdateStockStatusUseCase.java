package be.kdg.backend.restaurant.port.in;

import java.util.UUID;

public interface UpdateStockStatusUseCase {
    void markOutOfStock(UUID restaurantId,UUID dishId);
    void markInStock(UUID restaurantId,UUID dishId);
}
