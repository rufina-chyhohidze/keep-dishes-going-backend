package be.kdg.backend.restaurant.adapter.out.restaurant_order_projection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantOrderProjectionRepository extends JpaRepository<RestaurantOrderProjectionJpaEntity, UUID> {
    Optional<RestaurantOrderProjectionJpaEntity> findByRestaurantId(UUID restaurantId);
}
