package be.kdg.backend.restaurant.adapter.out.dish;

import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.domain.DishType;
import be.kdg.backend.restaurant.domain.FoodTag;
import be.kdg.backend.restaurant.port.out.LoadDishPort;
import be.kdg.backend.restaurant.port.out.SaveDishPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DishJpaAdapter  implements LoadDishPort, SaveDishPort {
    private final DishJpaRepository repository;

    public DishJpaAdapter(DishJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Dish> loadById(UUID dishId) {
        return repository.findById(dishId)
                .map(this::mapToDomain);
    }
    @Override
    public Dish save(Dish dish) {
        DishJpaEntity entity = mapToEntity(dish);
        repository.save(entity);
        return dish;
    }

    private Dish mapToDomain(DishJpaEntity e) {
        Set<FoodTag> tags = Set.of();

        if (e.getFoodTags() != null && !e.getFoodTags().isBlank()) {
            tags = Stream.of(e.getFoodTags()
                            .replace("{", "")
                            .replace("}", "")
                            .split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(s -> FoodTag.valueOf(s.toUpperCase()))
                    .collect(Collectors.toSet());
        }

        return Dish.rehydrate(
                e.getDishId(),
                e.getRestaurantId(),
                e.getName(),
                DishType.valueOf(e.getType().toUpperCase()),
                tags,
                e.getDescription(),
                e.getPrice(),
                e.getPictureUrl(),
                e.getAvailability(),
                e.getStockStatus()
        );
    }


    private DishJpaEntity mapToEntity(Dish dish) {
        String tags = dish.getFoodTags().stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));

        return new DishJpaEntity(
                dish.getDishId(),
                dish.getRestaurantId(),
                dish.getName(),
                dish.getType().name(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl(),
                dish.getAvailability(),
                dish.getStockStatus(),
                tags

        );
    }


}
