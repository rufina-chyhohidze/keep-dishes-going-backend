package be.kdg.backend.order.port.in;

import java.util.UUID;

public interface AcceptOrderUseCase {
    void acceptOrder(UUID orderId);
}
