package be.kdg.backend.restaurant.port.in.request;

import be.kdg.backend.restaurant.domain.Address;
import be.kdg.backend.restaurant.domain.OpeningHours;

import java.util.UUID;

public record CreateRestaurantCommand(
        UUID ownerId,
        String name,
        Address address,
        String contactEmail,
        String pictureUrl,
        String cuisineType,
        int defaultPreparationTime,
        OpeningHours openingHours
) {
}
