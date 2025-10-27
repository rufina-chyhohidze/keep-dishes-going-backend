package be.kdg.backend.order.adapter.out.mapper;

import be.kdg.backend.order.adapter.out.OrderJpaEntity;
import be.kdg.backend.order.domain.*;

import java.util.List;

public class OrderMapper {
    public static Order toDomain(OrderJpaEntity entity) {
        CustomerInfo customerInfo = new CustomerInfo(
                entity.getName(),
                entity.getEmail(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getCountry()
        );

        List<OrderLine> orderLines = entity.getOrderLines().stream()
                .map(line -> new OrderLine(
                        line.getDishId(),
                        line.getQuantity(),
                        line.getPriceAtCheckout()
                ))
                .toList();

        Payment payment = new Payment(
                entity.getPaymentId(),
                entity.getProvider(),
                PaymentStatus.valueOf(entity.getPaymentStatus())
        );

        return new Order(
                entity.getOrderId(),
                entity.getRestaurantId(),
                customerInfo,
                orderLines,
                payment,
                OrderStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt()
        );
    }

    public static OrderJpaEntity toJpaEntity(Order domain) {
        return new OrderJpaEntity(domain);
    }
}
