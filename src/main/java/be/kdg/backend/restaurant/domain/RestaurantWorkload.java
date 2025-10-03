package be.kdg.backend.restaurant.domain;

import java.util.UUID;

//projection. Restaurant owneer sees this "how busy my kitchen is"

/**
 * The projection is where Restaurant updates its “busyness factor” or “open orders” counter whenever a new order comes in.
 */
public class RestaurantWorkload {
    private UUID restaurantId;
    private int pendingOrdersCount;

    public RestaurantWorkload(UUID restaurantId, int pendingOrdersCount) {
        this.restaurantId = restaurantId;
        this.pendingOrdersCount = pendingOrdersCount;
    }

    public void increasePendingOrders() {
        this.pendingOrdersCount++;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public int getPendingOrders() {
        return pendingOrdersCount;
    }

    @Override
    public String toString() {
        return "RestaurantWorkload{" +
                "restaurantId=" + restaurantId +
                ", pendingOrdersCount=" + pendingOrdersCount +
                '}';
    }
}
