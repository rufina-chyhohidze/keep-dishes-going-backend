package be.kdg.backend.order.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderLine(UUID dishId, int quantity, BigDecimal priceAtCheckout) {
    public double totalPrice() {
        return priceAtCheckout.doubleValue() * quantity;
    }
}
