package be.kdg.backend.common.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderRejectedEvent(LocalDateTime eventPit,
                                 UUID orderId,
                                 UUID restaurantId,
                                 String reason) implements DomainEvent
{
    public OrderRejectedEvent(UUID orderId, UUID restaurantId, String reason) {
        this(LocalDateTime.now(), orderId, restaurantId, reason);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}


