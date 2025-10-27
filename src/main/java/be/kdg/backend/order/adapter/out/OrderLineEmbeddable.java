package be.kdg.backend.order.adapter.out;

import be.kdg.backend.order.domain.OrderLine;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
public class OrderLineEmbeddable {
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
