package be.kdg.backend.order.port.in.request;

import java.util.UUID;

public record AcceptOrderCommand(UUID orderId) {
}
