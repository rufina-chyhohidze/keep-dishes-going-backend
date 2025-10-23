package be.kdg.backend.order.port.in;

import be.kdg.backend.order.port.in.request.MarkOrderReadyCommand;

import java.util.UUID;

public interface MarkOrderReadyUseCase {
    void markOrderReady(MarkOrderReadyCommand command);
}
