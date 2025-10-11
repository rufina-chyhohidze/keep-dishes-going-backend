package be.kdg.backend.order.port.in.request;

import java.util.UUID;

public record RemoveItemFromBasketCommand(UUID basketId, UUID dishId) {
}
