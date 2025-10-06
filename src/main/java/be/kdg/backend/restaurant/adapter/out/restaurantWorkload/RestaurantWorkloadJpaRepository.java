package be.kdg.backend.restaurant.adapter.out.restaurantWorkload;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantWorkloadJpaRepository extends JpaRepository<RestaurantWorkloadJpaEntity, UUID> {
}
