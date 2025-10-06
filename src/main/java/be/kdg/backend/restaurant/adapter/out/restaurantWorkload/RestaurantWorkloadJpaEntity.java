package be.kdg.backend.restaurant.adapter.out.restaurantWorkload;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "restaurant_workload", schema = "kdg_restaurant")
public class RestaurantWorkloadJpaEntity {
    @Id
    private UUID restaurantId;

    private int pendingOrders;

    protected RestaurantWorkloadJpaEntity() {}

    public RestaurantWorkloadJpaEntity(UUID restaurantId, int pendingOrders) {
        this.restaurantId = restaurantId;
        this.pendingOrders = pendingOrders;
    }

    public UUID getRestaurantId() { return restaurantId; }
    public int getPendingOrders() { return pendingOrders; }
}


