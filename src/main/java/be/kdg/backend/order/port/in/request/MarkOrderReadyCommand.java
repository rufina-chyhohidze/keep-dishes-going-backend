package be.kdg.backend.order.port.in.request;

import java.util.UUID;

public record MarkOrderReadyCommand(UUID orderId) {
}
