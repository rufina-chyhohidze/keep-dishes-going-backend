package be.kdg.backend.order.adapter.out;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.out.PublishOrderEventsPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher implements PublishOrderEventsPort {
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Order publish(Order order) {
        order.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        return order;
    }
}
