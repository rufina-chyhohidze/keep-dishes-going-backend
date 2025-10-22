package be.kdg.backend.order.core;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.in.AcceptOrderUseCase;
import be.kdg.backend.order.port.out.LoadOrderPort;
import be.kdg.backend.order.port.out.SaveOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class AcceptOrderUseCaseImpl implements AcceptOrderUseCase {
    private final LoadOrderPort loadOrderPort;
    private final SaveOrderPort saveOrderPort;
    private final ApplicationEventPublisher publisher;

    public AcceptOrderUseCaseImpl(LoadOrderPort loadOrderPort, SaveOrderPort saveOrderPort, ApplicationEventPublisher publisher) {
        this.loadOrderPort = loadOrderPort;
        this.saveOrderPort = saveOrderPort;
        this.publisher = publisher;
    }
    @Override
    public void acceptOrder(UUID orderId) {
        Order order = loadOrderPort.load(orderId);
        order.accept();
        saveOrderPort.save(order);
        order.getDomainEvents().forEach(publisher::publishEvent);
    }

}
