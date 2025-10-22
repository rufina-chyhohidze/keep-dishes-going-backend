package be.kdg.backend.order.core;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.out.LoadPendingOrdersPort;
import be.kdg.backend.order.port.out.SaveOrderPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

/**
 * a background worker that checks pending orders and calls reject if PLACED for > 5 min.
 */
@Component
public class OrderAutoRejectScheduler {
    private final LoadPendingOrdersPort loadPendingOrdersPort;
    private final SaveOrderPort saveOrderPort;
    private final ApplicationEventPublisher publisher;

    public OrderAutoRejectScheduler(LoadPendingOrdersPort loadPendingOrdersPort, SaveOrderPort saveOrderPort, ApplicationEventPublisher publisher) {
        this.loadPendingOrdersPort = loadPendingOrdersPort;
        this.saveOrderPort = saveOrderPort;
        this.publisher = publisher;
    }

    @Scheduled(fixedDelay = 60_000)
    public void autoReject() {
        List<Order> pending = loadPendingOrdersPort.findPendingOlderThan(Duration.ofMinutes(5));
        for (Order order : pending) {
            order.reject("No response from restaurant within 5 minutes");
            saveOrderPort.save(order);
            order.getDomainEvents().forEach(publisher::publishEvent);
        }
    }

}
