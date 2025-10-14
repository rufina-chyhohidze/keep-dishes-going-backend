package be.kdg.backend.restaurant.adapter.out.restaurant_sales_snapshot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * one row per restaurant, snapshot
 */
@Entity
@Table(name = "restaurant_sales_snapshot", schema = "kdg_restaurant")
public class RestaurantSalesSnapshotEntity {
    @Id
    private UUID restaurantId;

    @Column(nullable = false)
    private double totalRevenue;

    @Column(nullable = false)
    private int totalOrders;

    protected RestaurantSalesSnapshotEntity() {}

    public RestaurantSalesSnapshotEntity(UUID restaurantId, double totalRevenue, int totalOrders) {
        this.restaurantId = restaurantId;
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
}
