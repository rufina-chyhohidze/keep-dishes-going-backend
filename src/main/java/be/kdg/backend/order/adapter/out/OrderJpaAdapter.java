package be.kdg.backend.order.adapter.out;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.out.SaveOrderPort;
import org.springframework.stereotype.Repository;

@Repository
public class OrderJpaAdapter implements SaveOrderPort {
    private final OrderJpaRepository repo;

    public OrderJpaAdapter(OrderJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(Order order) {
        repo.save(new OrderJpaEntity(order));
    }
}
