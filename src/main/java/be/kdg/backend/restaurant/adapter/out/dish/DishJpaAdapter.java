package be.kdg.backend.restaurant.adapter.out.dish;

import be.kdg.backend.restaurant.adapter.out.mapper.DishMapper;
import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.domain.DishType;
import be.kdg.backend.restaurant.domain.FoodTag;
import be.kdg.backend.restaurant.port.out.LoadDishPort;
import be.kdg.backend.restaurant.port.out.SaveDishPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DishJpaAdapter  implements LoadDishPort, SaveDishPort {
    private final DishJpaRepository repository;
    private DishMapper dishMapper;

    public DishJpaAdapter(DishJpaRepository repository, DishMapper dishMapper) {
        this.repository = repository;
        this.dishMapper = dishMapper;
    }

    @Override
    public Optional<Dish> loadById(UUID dishId) {
        return repository.findById(dishId)
                .map(dishMapper::toDomain);
    }

    @Override
    public List<Dish> loadByRestaurantId(UUID restaurantId) {
        return repository.findByRestaurantId(restaurantId)
                .stream()
                .map(dishMapper::toDomain)
                .toList();
    }

    @Override
    public Dish save(Dish dish) {
        repository.save(dishMapper.toEntity(dish));
        return dish;
    }
    @Override
    public Optional<Dish> loadByIdAndRestaurantId(UUID dishId, UUID restaurantId) {
        return repository.findByDishIdAndRestaurantId(dishId, restaurantId)
                .map(dishMapper::toDomain);
    }



}
