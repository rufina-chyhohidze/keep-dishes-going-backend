package be.kdg.backend.restaurant.adapter.out.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantJpaRepository  extends JpaRepository<RestaurantJpaEntity, UUID> {
    Optional<RestaurantJpaEntity> findByOwnerId(UUID ownerId);
}
