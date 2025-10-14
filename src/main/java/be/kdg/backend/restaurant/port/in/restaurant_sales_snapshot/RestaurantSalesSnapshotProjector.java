package be.kdg.backend.restaurant.port.in.restaurant_sales_snapshot;

public interface RestaurantSalesSnapshotProjector {
    void project(RestaurantSalesSnapshotCommand restaurantSalesSnapshotCommand);
}
