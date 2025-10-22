package be.kdg.backend.order.port.out;

import be.kdg.backend.order.domain.Order;

import java.util.UUID;

public interface LoadOrderPort {
    Order load(UUID orderId);
}
