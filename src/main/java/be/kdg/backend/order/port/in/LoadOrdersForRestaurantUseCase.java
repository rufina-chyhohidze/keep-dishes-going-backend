package be.kdg.backend.order.port.in;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.domain.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface LoadOrdersForRestaurantUseCase {
    List<Order> findOrdersByRestaurantAndStatus(UUID restaurantId, OrderStatus status);

}
