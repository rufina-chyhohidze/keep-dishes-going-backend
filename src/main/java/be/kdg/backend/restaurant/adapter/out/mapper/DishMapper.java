package be.kdg.backend.restaurant.adapter.out.mapper;

import be.kdg.backend.restaurant.adapter.out.dish.DishJpaEntity;
import be.kdg.backend.restaurant.adapter.out.dish.enums.DishJpaAvailability;
import be.kdg.backend.restaurant.adapter.out.dish.enums.FoodJpaTag;
import be.kdg.backend.restaurant.adapter.out.dish.enums.StockJpaStatus;
import be.kdg.backend.restaurant.domain.*;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DishMapper {

    public Dish toDomain(DishJpaEntity e) {
        Set<FoodTag> tags = Set.of();

        if (e.getFoodTags() != null) {
            tags = Set.of(FoodTag.valueOf(e.getFoodTags().name()));
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
                DishAvailability.valueOf(e.getAvailability().toString()),
                StockStatus.valueOf(e.getStockStatus().toString())
        );
    }

    public DishJpaEntity toEntity(Dish dish) {
        FoodJpaTag tag = null;
        if (!dish.getFoodTags().isEmpty()) {
            // Currently taking the first tag — you can extend this if you later support multiple tags
            tag = FoodJpaTag.valueOf(dish.getFoodTags().iterator().next().name());
        }

        return new DishJpaEntity(
                dish.getDishId(),
                dish.getRestaurantId(),
                dish.getName(),
                dish.getType().name(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl(),
                DishJpaAvailability.valueOf(dish.getAvailability().toString()),
                StockJpaStatus.valueOf(dish.getStockStatus().toString()),
                tag
        );
    }
}

