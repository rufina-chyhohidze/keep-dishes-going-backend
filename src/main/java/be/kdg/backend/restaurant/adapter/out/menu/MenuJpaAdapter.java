package be.kdg.backend.restaurant.adapter.out.menu;

import be.kdg.backend.restaurant.adapter.out.dish.DishJpaRepository;
import be.kdg.backend.restaurant.adapter.out.mapper.DishMapper;
import be.kdg.backend.restaurant.adapter.out.mapper.MenuMapper;
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
    private final MenuJpaRepository menuRepository;
    private final DishJpaRepository dishRepository;
    private final DishMapper dishMapper;
    private final MenuMapper menuMapper;

    public MenuJpaAdapter(
            MenuJpaRepository menuRepository,
            DishJpaRepository dishRepository,
            DishMapper dishMapper,
            MenuMapper menuMapper
    ) {
        this.menuRepository = menuRepository;
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
        this.menuMapper = menuMapper;
    }

    @Override
    public Optional<Menu> loadMenuByRestaurantId(UUID restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId)
                .map(menuMapper::toDomain)
                .map(menu -> {
                    List<Dish> dishes = dishRepository.findByRestaurantId(restaurantId)
                            .stream()
                            .map(dishMapper::toDomain)
                            .toList();
                    dishes.forEach(menu::addDish);
                    return menu;
                });
    }

    @Override
    public void save(Menu menu) {
        menuRepository.save(menuMapper.toEntity(menu));

        menu.getDishes().forEach(dish ->
                dishRepository.save(dishMapper.toEntity(dish))
        );
    }

}
