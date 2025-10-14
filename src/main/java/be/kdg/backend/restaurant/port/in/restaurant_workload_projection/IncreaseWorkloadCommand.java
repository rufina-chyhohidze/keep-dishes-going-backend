package be.kdg.backend.restaurant.port.in.restaurant_workload_projection;

import java.util.UUID;

public record IncreaseWorkloadCommand(UUID restaurantId) {
}
