package be.kdg.backend.order.adapter.out;

import be.kdg.backend.order.domain.*;
import be.kdg.backend.order.port.out.LoadOrderPort;
import be.kdg.backend.order.port.out.LoadPendingOrdersPort;
import be.kdg.backend.order.port.out.SaveOrderPort;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderJpaAdapter implements SaveOrderPort, LoadOrderPort, LoadPendingOrdersPort {
    private final OrderJpaRepository repo;

    public OrderJpaAdapter(OrderJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void save(Order order) {
        repo.save(new OrderJpaEntity(order));
    }


    @Override
    public Order load(UUID orderId) {
        return repo.findByOrderId(orderId)
                .map(this::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
    }

    @Override
    public List<Order> findPendingOlderThan(Duration maxAge) {
        Instant threshold = Instant.now().minus(maxAge);
        return repo.findPendingOlderThan(threshold)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Order toDomain(OrderJpaEntity entity) {
        CustomerInfo customerInfo = new CustomerInfo(
                entity.getName(),
                entity.getEmail(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getCountry()
        );

        Payment payment = new Payment(
                entity.getPaymentId(),
                entity.getProvider(),
                PaymentStatus.valueOf(entity.getPaymentStatus())
        );

        List<OrderLine> lines = entity.getOrderLines().stream()
                .map(e -> new OrderLine(e.getDishId(), e.getQuantity(), e.getPriceAtCheckout()))
                .toList();

        return new Order(
                entity.getOrderId(),
                entity.getRestaurantId(),
                customerInfo,
                lines,
                payment,
                OrderStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt()
        );
    }
    }