package be.kdg.backend.restaurant.adapter.out.restaurant_sales_snapshot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantSalesSnapshotRepository extends JpaRepository<RestaurantSalesSnapshotEntity, UUID> {
}
