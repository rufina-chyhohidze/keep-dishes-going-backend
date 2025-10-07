package be.kdg.backend.restaurant.adapter.out.mapper;

import be.kdg.backend.restaurant.adapter.out.dish.DishJpaEntity;
import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.domain.DishType;
import be.kdg.backend.restaurant.domain.FoodTag;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DishMapper {
    public Dish toDomain(DishJpaEntity e) {
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

        return new Dish(
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

    public DishJpaEntity toEntity(Dish dish) {
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
