package be.kdg.backend.restaurant.adapter.out;

import be.kdg.backend.restaurant.adapter.out.dish.DishJpaRepository;
import be.kdg.backend.restaurant.adapter.out.mapper.DishMapper;
import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.domain.Menu;
import be.kdg.backend.restaurant.port.out.LoadMenuPort;
import be.kdg.backend.restaurant.port.out.SaveMenuPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MenuJpaAdapter implements LoadMenuPort, SaveMenuPort {
    private final DishJpaRepository dishRepository;
    private final DishMapper dishMapper;

    public MenuJpaAdapter(DishJpaRepository dishRepository, DishMapper dishMapper) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
    }

    @Override
    public Optional<Menu> loadMenuByRestaurantId(UUID restaurantId) {
        List<Dish> dishes = dishRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(dishMapper::toDomain)
                .toList();

        if (dishes.isEmpty()) return Optional.empty();


        return Optional.of(new Menu(UUID.randomUUID(), restaurantId, dishes));
    }

    @Override
    public void save(Menu menu) {
        menu.getDishes().forEach(dish ->
                dishRepository.save(dishMapper.toEntity(dish))
        );
    }
}
