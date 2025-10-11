package be.kdg.backend.order.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class BasketItem {
    private final UUID dishId;
    private final String dishName;
    private final BigDecimal unitPrice;
    private int quantity;

    public BasketItem(UUID dishId, String dishName, BigDecimal unitPrice, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        this.dishId = Objects.requireNonNull(dishId);
        this.dishName = Objects.requireNonNull(dishName);
        this.unitPrice = Objects.requireNonNull(unitPrice);
        this.quantity = quantity;
    }

    public UUID getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        this.quantity += amount;
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
        this.quantity = newQuantity;
    }

    public BigDecimal totalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
