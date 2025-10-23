package be.kdg.backend.order.core;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.in.MarkOrderReadyUseCase;
import be.kdg.backend.order.port.in.request.MarkOrderReadyCommand;
import be.kdg.backend.order.port.out.LoadOrderPort;
import be.kdg.backend.order.port.out.PublishOrderEventsPort;
import be.kdg.backend.order.port.out.SaveOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class MarkOrderReadyUseCaseImpl implements MarkOrderReadyUseCase {

    private final LoadOrderPort loadOrderPort;
    private final SaveOrderPort saveOrderPort;
    private final PublishOrderEventsPort publishOrderEventsPort;

    public MarkOrderReadyUseCaseImpl(LoadOrderPort loadOrderPort,
                                     SaveOrderPort saveOrderPort,
                                     PublishOrderEventsPort publishOrderEventsPort) {
        this.loadOrderPort = loadOrderPort;
        this.saveOrderPort = saveOrderPort;
        this.publishOrderEventsPort = publishOrderEventsPort;
    }

    @Override
    public void markOrderReady(MarkOrderReadyCommand command) {
        Order order = loadOrderPort.load(command.orderId());
        order.markReady();
        saveOrderPort.save(order);
        publishOrderEventsPort.publish(order);
    }
}
