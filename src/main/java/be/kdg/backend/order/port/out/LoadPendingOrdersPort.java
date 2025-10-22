package be.kdg.backend.order.port.out;

import be.kdg.backend.order.domain.Order;

import java.time.Duration;
import java.util.List;

public interface LoadPendingOrdersPort {
    List<Order> findPendingOlderThan(Duration maxAge);
}
