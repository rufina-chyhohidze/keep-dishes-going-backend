package be.kdg.backend.restaurant.port.in;

import be.kdg.backend.restaurant.port.in.request.EditDishCommand;

import java.util.UUID;

public interface EditDishUseCase {
    UUID editDish(EditDishCommand command);
}
