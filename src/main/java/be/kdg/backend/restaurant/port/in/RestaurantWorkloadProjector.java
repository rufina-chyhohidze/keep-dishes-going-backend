package be.kdg.backend.restaurant.port.in;

import be.kdg.backend.restaurant.port.in.request.IncreaseWorkloadCommand;

public interface RestaurantWorkloadProjector {
    void project (IncreaseWorkloadCommand command);
}
