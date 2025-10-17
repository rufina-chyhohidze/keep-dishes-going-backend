package be.kdg.backend.restaurant.adapter.out.dish;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DishJpaRepository extends JpaRepository<DishJpaEntity, UUID> {
    List<DishJpaEntity> findByRestaurantId(UUID restaurantId);
    Optional<DishJpaEntity> findByDishIdAndRestaurantId(UUID dishId, UUID restaurantId);

}
