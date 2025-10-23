package be.kdg.backend.restaurant.port.in.restaurant_order_projection;

import be.kdg.backend.restaurant.port.in.request.OrderAcceptedProjectionCommand;
import be.kdg.backend.restaurant.port.in.request.OrderReadyProjectionCommand;
import be.kdg.backend.restaurant.port.in.request.OrderRejectedProjectionCommand;

public interface RestaurantOrderProjectionProjector {
    void project(OrderAcceptedProjectionCommand command);
    void project(OrderReadyProjectionCommand command);
    void project(OrderRejectedProjectionCommand command);
}
