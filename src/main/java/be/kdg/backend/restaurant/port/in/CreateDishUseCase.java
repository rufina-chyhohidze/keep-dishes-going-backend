package be.kdg.backend.restaurant.port.in;

import java.util.UUID;

public interface CreateDishUseCase {
    UUID createDish(CreateDishCommand command);
}
