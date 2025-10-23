package be.kdg.backend.restaurant.port.in.request;

import java.util.UUID;

public record OrderReadyProjectionCommand(UUID restaurantId) {
}
