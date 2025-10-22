package be.kdg.backend.restaurant.adapter.in.requests;

import java.math.BigDecimal;
import java.util.Set;

public record CreateDishRequest(
                                String name,
                                String type,
                                Set<String> foodTags,
                                String description,
                                BigDecimal price,
                                String pictureUrl) {
}
