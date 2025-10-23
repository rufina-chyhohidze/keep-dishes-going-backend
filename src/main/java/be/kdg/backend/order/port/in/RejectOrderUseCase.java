package be.kdg.backend.order.port.in;

import be.kdg.backend.order.port.in.request.RejectOrderCommand;

import java.util.UUID;

public interface RejectOrderUseCase {
    void rejectOrder(RejectOrderCommand command);
}
