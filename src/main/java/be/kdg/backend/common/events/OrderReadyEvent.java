package be.kdg.backend.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderReadyEvent(LocalDateTime eventPit,
                              UUID orderId,
                              UUID restaurantId) implements DomainEvent{
    public OrderReadyEvent(UUID orderId, UUID restaurantId) {
        this(LocalDateTime.now(), orderId, restaurantId);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
