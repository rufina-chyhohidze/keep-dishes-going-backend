package be.kdg.backend.restaurant.adapter.out;

import be.kdg.backend.restaurant.adapter.out.dish.DishJpaEntity;
import be.kdg.backend.restaurant.adapter.out.dish.DishJpaRepository;
import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.domain.DishType;
import be.kdg.backend.restaurant.domain.Menu;
import be.kdg.backend.restaurant.port.out.LoadMenuPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MenuJpaAdapter implements LoadMenuPort {
    private final DishJpaRepository dishRepository;

    public MenuJpaAdapter(DishJpaRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Optional<Menu> loadMenuByRestaurantId(UUID restaurantId) {
        List<DishJpaEntity> dishEntities = dishRepository.findByRestaurantId(restaurantId);
        if (dishEntities.isEmpty()) return Optional.empty();

        List<Dish> dishes = dishEntities.stream()
                .map(e -> new Dish(
                        e.getDishId(),
                        e.getRestaurantId(),
                        e.getName(),
                        DishType.valueOf(e.getType().toUpperCase()),
                        Dish.parseFoodTags(e.getFoodTags()),
                        e.getDescription(),
                        e.getPrice(),
                        e.getPictureUrl(),
                        e.getAvailability(),
                        e.getStockStatus()
                ))
                .toList();

        return Optional.of(new Menu(UUID.randomUUID(), restaurantId, dishes));
    }
}
