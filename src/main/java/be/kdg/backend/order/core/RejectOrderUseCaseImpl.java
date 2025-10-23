package be.kdg.backend.order.core;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.in.AcceptOrderUseCase;
import be.kdg.backend.order.port.in.RejectOrderUseCase;
import be.kdg.backend.order.port.in.request.RejectOrderCommand;
import be.kdg.backend.order.port.out.LoadOrderPort;
import be.kdg.backend.order.port.out.PublishOrderEventsPort;
import be.kdg.backend.order.port.out.SaveOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class RejectOrderUseCaseImpl implements RejectOrderUseCase {
    private final LoadOrderPort loadOrderPort;
    private final SaveOrderPort saveOrderPort;
    private final PublishOrderEventsPort publishOrderEventsPort;

    public RejectOrderUseCaseImpl(
            LoadOrderPort loadOrderPort,
            SaveOrderPort saveOrderPort,
            PublishOrderEventsPort publishOrderEventsPort) {
        this.loadOrderPort = loadOrderPort;
        this.saveOrderPort = saveOrderPort;
        this.publishOrderEventsPort = publishOrderEventsPort;
    }

    @Override
    public void rejectOrder(RejectOrderCommand command) {
        Order order = loadOrderPort.load(command.orderId());
        order.reject(command.reason());
        saveOrderPort.save(order);
        publishOrderEventsPort.publish(order);
    }

}
