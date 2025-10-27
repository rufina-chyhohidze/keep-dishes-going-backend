package be.kdg.backend.order.core;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.domain.OrderStatus;
import be.kdg.backend.order.port.in.LoadOrdersForRestaurantUseCase;
import be.kdg.backend.order.port.out.LoadOrdersByRestaurantPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LoadOrdersForRestaurantUseCaseImpl implements LoadOrdersForRestaurantUseCase {
    private final LoadOrdersByRestaurantPort loadOrdersByRestaurantPort;

    public LoadOrdersForRestaurantUseCaseImpl(LoadOrdersByRestaurantPort loadOrdersByRestaurantPort) {
        this.loadOrdersByRestaurantPort = loadOrdersByRestaurantPort;
    }

    @Override
    public List<Order> findOrdersByRestaurantAndStatus(UUID restaurantId, OrderStatus status) {
        return loadOrdersByRestaurantPort.findOrdersByRestaurantAndStatus(restaurantId, status);
    }
}
