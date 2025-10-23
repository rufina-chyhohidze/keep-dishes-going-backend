package be.kdg.backend.order.port.out;

import be.kdg.backend.order.domain.Order;

public interface PublishOrderEventsPort {
    Order publish(Order order);
}
