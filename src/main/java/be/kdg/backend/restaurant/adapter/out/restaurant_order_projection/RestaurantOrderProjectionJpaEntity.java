package be.kdg.backend.restaurant.adapter.out.restaurant_order_projection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table (name = "restaurant_order_projection", schema = "kdg_restaurant")
public class RestaurantOrderProjectionJpaEntity {
    @Id
    private UUID restaurantId;

    private int acceptedCount;
    private int readyCount;
    private int rejectedCount;

    protected RestaurantOrderProjectionJpaEntity() {}

    public RestaurantOrderProjectionJpaEntity(UUID restaurantId, int acceptedCount, int readyCount, int rejectedCount) {
        this.restaurantId = restaurantId;
        this.acceptedCount = acceptedCount;
        this.readyCount = readyCount;
        this.rejectedCount = rejectedCount;
    }

    public UUID getRestaurantId() { return restaurantId; }
    public int getAcceptedCount() { return acceptedCount; }
    public int getReadyCount() { return readyCount; }
    public int getRejectedCount() { return rejectedCount; }

    public void incrementAccepted() { acceptedCount++; }
    public void moveAcceptedToReady() {
        acceptedCount--;
        readyCount++;
    }
    public void moveAcceptedToRejected() {
        if (this.acceptedCount > 0) {
            this.acceptedCount--;
        }
        this.rejectedCount++;
    }

}
