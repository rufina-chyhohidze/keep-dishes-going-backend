package be.kdg.backend.order.port.out;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.domain.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface LoadOrdersByRestaurantPort {
    List<Order> findOrdersByRestaurantAndStatus(UUID restaurantId, OrderStatus status);
}
