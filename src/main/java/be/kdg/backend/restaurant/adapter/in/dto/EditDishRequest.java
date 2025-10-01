package be.kdg.backend.restaurant.adapter.in.dto;

import java.math.BigDecimal;
import java.util.List;

public record EditDishRequest(
        String name,
        String type,              // "starter" | "main" | "dessert" (case-insensitive)
        List<String> foodTags,    // ["vegan","glutenFree","LACTOSE"] etc.
        String description,
        BigDecimal price,
        String pictureUrl
)  {
}
