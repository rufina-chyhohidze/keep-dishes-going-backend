package be.kdg.backend.order.port.in;

import java.util.UUID;

public interface RejectOrderUseCase {
    void rejectOrder(UUID orderId,String reason);
}
