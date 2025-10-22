package be.kdg.backend.restaurant.adapter.in.response;

import be.kdg.backend.restaurant.domain.Dish;
import be.kdg.backend.restaurant.domain.DishAvailability;
import be.kdg.backend.restaurant.domain.StockStatus;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record DishResponse(
        UUID dishId,
        String name,
        String type,
        Set<String> foodTags,
        String description,
        BigDecimal price,
        String pictureUrl,
        DishAvailability availability,
        StockStatus stockStatus
) {
    public static DishResponse fromDomain(Dish dish) {
        Set<String> tags = dish.getFoodTags().stream()
                .map(Enum::name)
                .collect(java.util.stream.Collectors.toSet());

        return new DishResponse(
                dish.getDishId(),
                dish.getName(),
                dish.getType().name(),
                tags,
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl(),
                dish.getAvailability(),
                dish.getStockStatus()
        );
    }
}
