package be.kdg.backend.restaurant.port.in.request;

import be.kdg.backend.restaurant.domain.DishType;
import be.kdg.backend.restaurant.domain.FoodTag;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record EditDishCommand (UUID dishId,
                               UUID restaurantId,
                               String name,
                               DishType type,
                               Set<FoodTag> foodTags,
                               String description,
                               BigDecimal price,
                               String pictureUrl){
}
