package be.kdg.backend.order.port.out;

import be.kdg.backend.order.domain.Order;

public interface SaveOrderPort {
    void save(Order order);
}
