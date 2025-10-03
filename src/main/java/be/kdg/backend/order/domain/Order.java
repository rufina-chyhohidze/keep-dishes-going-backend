package be.kdg.backend.order.domain;

import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final UUID restaurantId;
    private final CustomerInfo customerInfo;
    private final List<OrderLine> orderLines;
    private final Payment paymentInfo;
    private OrderStatus status;

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
    }

    public UUID getOrderId() { return orderId; }
    public UUID getRestaurantId() { return restaurantId; }
    public CustomerInfo getCustomerInfo() { return customerInfo; }
    public List<OrderLine> getOrderLines() { return orderLines; }
    public Payment getPaymentInfo() { return paymentInfo; }
    public OrderStatus getStatus() { return status; }

    public double calculateTotalPrice() {
        return orderLines.stream().mapToDouble(OrderLine::totalPrice).sum();
    }

    public void accept() { this.status = OrderStatus.ACCEPTED; }
    public void reject() { this.status = OrderStatus.REJECTED; }
}
