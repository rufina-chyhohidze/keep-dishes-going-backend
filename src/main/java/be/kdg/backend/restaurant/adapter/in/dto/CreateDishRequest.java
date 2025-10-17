package be.kdg.backend.restaurant.adapter.in.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CreateDishRequest(
                                String name,
                                String type,
                                Set<String> foodTags,
                                String description,
                                BigDecimal price,
                                String pictureUrl) {
}
