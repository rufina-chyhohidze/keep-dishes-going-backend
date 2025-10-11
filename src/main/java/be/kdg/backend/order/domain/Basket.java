package be.kdg.backend.order.domain;

import java.math.BigDecimal;
import java.util.*;

public class Basket {
    private final UUID basketId;
    private final UUID restaurantId;
    private final List<BasketItem> items;
    private BasketStatus status;

    public Basket(UUID basketId, UUID restaurantId) {
        this.basketId = Objects.requireNonNull(basketId);
        this.restaurantId = Objects.requireNonNull(restaurantId);
        this.items = new ArrayList<>();
        this.status = BasketStatus.ACTIVE;
    }

    public Basket(UUID basketId, UUID restaurantId, List<BasketItem> items, BasketStatus status) {
        this.basketId = basketId;
        this.restaurantId = restaurantId;
        this.items = new ArrayList<>(items);
        this.status = status;
    }

    public UUID getBasketId() {
        return basketId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public BasketStatus getStatus() {
        return status;
    }

    public List<BasketItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(UUID dishId, String dishName, BigDecimal price, int quantity) {
        ensureActive();
        items.stream()
                .filter(i -> i.getDishId().equals(dishId))
                .findFirst()
                .ifPresentOrElse(
                        i -> i.increaseQuantity(quantity),
                        () -> items.add(new BasketItem(dishId, dishName, price, quantity))
                );
    }

    public void updateQuantity(UUID dishId, int quantity) {
        ensureActive();
        items.stream()
                .filter(i -> i.getDishId().equals(dishId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Dish not found in basket"))
                .updateQuantity(quantity);
    }

    public void removeItem(UUID dishId) {
        ensureActive();
        items.removeIf(i -> i.getDishId().equals(dishId));
    }

    public void lock() {
        ensureActive();
        if (items.isEmpty()) throw new IllegalStateException("Cannot lock empty basket");
        this.status = BasketStatus.LOCKED;
    }

    public void checkout() {
        if (status != BasketStatus.LOCKED) {
            throw new IllegalStateException("Basket must be locked before checkout.");
        }
        this.status = BasketStatus.CHECKED_OUT;
    }

    public BigDecimal totalPrice() {
        return items.stream()
                .map(BasketItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void ensureActive() {
        if (status != BasketStatus.ACTIVE) {
            throw new IllegalStateException("Basket is not active.");
        }
    }
}
