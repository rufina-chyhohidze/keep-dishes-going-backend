package be.kdg.backend.order.adapter.out;

import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.domain.OrderLine;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name= "orders", schema = "kdg_order")
public class OrderJpaEntity {
    @Id
    private UUID orderId;
    private UUID restaurantId;

    // customer info
    private String name;
    private String email;
    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String country;

    private double totalPrice;
    private String status;

    private UUID paymentId;
    private String provider;
    private String paymentStatus;
    private Instant createdAt;


    @ElementCollection
    @CollectionTable(name = "order_lines", schema = "kdg_order",
            joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLineEmbeddable> orderLines;

    protected OrderJpaEntity() {}

    public OrderJpaEntity(Order order) {
        this.orderId = order.getOrderId();
        this.restaurantId = order.getRestaurantId();
        this.name = order.getCustomerInfo().name();
        this.email = order.getCustomerInfo().email();
        this.street = order.getCustomerInfo().street();
        this.number = order.getCustomerInfo().number();
        this.postalCode = order.getCustomerInfo().postalCode();
        this.city = order.getCustomerInfo().city();
        this.country = order.getCustomerInfo().country();

        this.totalPrice = order.calculateTotalPrice();
        this.status = order.getStatus().name();

        this.paymentId = order.getPaymentInfo().getPaymentId();
        this.provider = order.getPaymentInfo().getProvider();
        this.createdAt = order.getCreatedAt();
        this.paymentStatus = order.getPaymentInfo().getStatus().name();
        this.orderLines = order.getOrderLines().stream()
                .map(OrderLineEmbeddable::new)
                .toList();
    }
    public UUID getOrderId() {
        return orderId;
    }
    public UUID getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public String getPostalCode() { return postalCode; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public UUID getPaymentId() { return paymentId; }
    public String getProvider() { return provider; }
    public String getPaymentStatus() { return paymentStatus; }
    public Instant getCreatedAt() { return createdAt; }
    public List<OrderLineEmbeddable> getOrderLines() { return orderLines; }


}


@Embeddable
class OrderLineEmbeddable {
    private UUID dishId;
    private int quantity;
    private BigDecimal priceAtCheckout;

    protected OrderLineEmbeddable() {}

    public OrderLineEmbeddable(OrderLine line) {
        this.dishId = line.dishId();
        this.quantity = line.quantity();
        this.priceAtCheckout = line.priceAtCheckout();
    }

    public UUID getDishId() { return dishId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPriceAtCheckout() { return priceAtCheckout; }
}

