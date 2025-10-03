package be.kdg.backend.restaurant.port.in.request;

import java.util.UUID;

public record IncreaseWorkloadCommand(UUID restaurantId) {
}
