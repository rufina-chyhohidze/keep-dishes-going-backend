package be.kdg.backend.restaurant.port.in;
import java.util.UUID;

public interface PublishDishUseCase {
void publishDish(UUID restaurantId, UUID dishId);
}
