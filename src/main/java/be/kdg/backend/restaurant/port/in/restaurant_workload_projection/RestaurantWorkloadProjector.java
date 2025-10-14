package be.kdg.backend.restaurant.port.in.restaurant_workload_projection;

public interface RestaurantWorkloadProjector {
    void project (IncreaseWorkloadCommand command);
}
