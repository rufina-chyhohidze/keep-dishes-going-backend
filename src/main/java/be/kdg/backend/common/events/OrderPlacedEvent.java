package be.kdg.backend.common.events;


import java.time.LocalDateTime;
import java.util.UUID;

public record OrderPlacedEvent(
        LocalDateTime eventPit,
        UUID orderId,
        UUID restaurantId,
        UUID customerId,
        double totalPrice
) implements DomainEvent {

    public OrderPlacedEvent(UUID orderId, UUID restaurantId, UUID customerId, double totalPrice) {
        this(LocalDateTime.now(), orderId, restaurantId, customerId, totalPrice);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}