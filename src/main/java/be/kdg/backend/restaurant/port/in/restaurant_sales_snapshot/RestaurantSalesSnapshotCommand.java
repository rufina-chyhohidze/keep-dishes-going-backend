package be.kdg.backend.restaurant.port.in.restaurant_sales_snapshot;

import java.util.UUID;

/**
 * command object passed from the listener to the projector
 */
public record RestaurantSalesSnapshotCommand(UUID restaurantId, double orderTotal) {}

