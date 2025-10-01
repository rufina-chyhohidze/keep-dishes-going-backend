package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Dish;

public interface SaveDishPort {
    Dish save(Dish dish);
}
