package be.kdg.backend.order.port.in;

import be.kdg.backend.order.port.in.request.AcceptOrderCommand;

import java.util.UUID;

public interface AcceptOrderUseCase {
    void acceptOrder(AcceptOrderCommand command);
}
