package be.kdg.backend.order.port.in;

import java.util.UUID;

public interface MarkOrderReadyUseCase {
    void markReady(UUID orderId);
}
