package be.kdg.backend.order.domain;

import be.kdg.backend.common.events.OrderAcceptedEvent;
import be.kdg.backend.common.events.OrderReadyEvent;
import be.kdg.backend.common.events.OrderRejectedEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//aggreagate
public class Order {
    private final UUID orderId;
    private final UUID restaurantId;
    private final CustomerInfo customerInfo;
    private final List<OrderLine> orderLines;
    private final Payment paymentInfo;
    private OrderStatus status;
    private final List<Object> domainEvents = new ArrayList<>();
    private final Instant createdAt;



    public Order(UUID orderId, UUID restaurantId,
                 CustomerInfo customerInfo,
                 List<OrderLine> orderLines,
                 Payment paymentInfo) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.customerInfo = customerInfo;
        this.orderLines = orderLines;
        this.paymentInfo = paymentInfo;
        this.status = OrderStatus.PLACED;
        this.createdAt = Instant.now();
    }

    //2nds constructor with no default status
    public Order(UUID orderId,
                 UUID restaurantId,
                 CustomerInfo customerInfo,
                 List<OrderLine> orderLines,
                 Payment paymentInfo,
                 OrderStatus status, Instant createdAt) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.customerInfo = customerInfo;
        this.orderLines = orderLines;
        this.paymentInfo = paymentInfo;
        this.status = status;
        this.createdAt = createdAt;
    }


    public UUID getOrderId() { return orderId; }
    public UUID getRestaurantId() { return restaurantId; }
    public CustomerInfo getCustomerInfo() { return customerInfo; }
    public List<OrderLine> getOrderLines() { return orderLines; }
    public Payment getPaymentInfo() { return paymentInfo; }
    public OrderStatus getStatus() { return status; }
    public List<Object> getDomainEvents() { return domainEvents; }
    public Instant getCreatedAt() { return createdAt; }

    public double calculateTotalPrice() {
        return orderLines.stream().mapToDouble(OrderLine::totalPrice).sum();
    }

    //We record domain events inside the aggregate, not publish them directly.
    public void accept() {
        if (status != OrderStatus.PLACED) throw new IllegalStateException("Cannot accept non-placed order");
        this.status = OrderStatus.ACCEPTED;
        domainEvents.add(new OrderAcceptedEvent(orderId, restaurantId));
    }
    public void reject(String reason) {
        if (status != OrderStatus.PLACED) throw new IllegalStateException("Cannot reject non-placed order");
        this.status = OrderStatus.REJECTED;
        domainEvents.add(new OrderRejectedEvent(orderId, restaurantId, reason));
    }

    public void markReady() {
        if (status != OrderStatus.ACCEPTED) throw new IllegalStateException("Only accepted orders can be marked ready");
        this.status = OrderStatus.READY;
        domainEvents.add(new OrderReadyEvent(orderId, restaurantId));
    }
}
