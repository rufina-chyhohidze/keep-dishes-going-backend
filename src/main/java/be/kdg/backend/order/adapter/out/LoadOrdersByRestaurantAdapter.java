package be.kdg.backend.order.adapter.out;

import be.kdg.backend.order.adapter.out.mapper.OrderMapper;
import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.domain.OrderStatus;
import be.kdg.backend.order.port.out.LoadOrdersByRestaurantPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class LoadOrdersByRestaurantAdapter implements LoadOrdersByRestaurantPort {
    private final OrderJpaRepository repo;

    public LoadOrdersByRestaurantAdapter(OrderJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Order> findOrdersByRestaurantAndStatus(UUID restaurantId, OrderStatus status) {
        return repo.findByRestaurantIdAndStatus(restaurantId, status.name())
                .stream()
                .map(OrderMapper::toDomain)
                .toList();
    }
}
