package be.kdg.backend.order.core;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.in.AcceptOrderUseCase;
import be.kdg.backend.order.port.in.RejectOrderUseCase;
import be.kdg.backend.order.port.out.LoadOrderPort;
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
    private final ApplicationEventPublisher publisher;

    public RejectOrderUseCaseImpl(LoadOrderPort loadOrderPort, SaveOrderPort saveOrderPort, ApplicationEventPublisher publisher) {
        this.loadOrderPort = loadOrderPort;
        this.saveOrderPort = saveOrderPort;
        this.publisher = publisher;
    }

    @Override
    public void rejectOrder(UUID orderId, String reason) {
        Order order = loadOrderPort.load(orderId);
        order.reject(reason);
        saveOrderPort.save(order);
        order.getDomainEvents().forEach(publisher::publishEvent);
    }

}
