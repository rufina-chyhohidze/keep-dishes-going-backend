package be.kdg.backend.restaurant.adapter.in.dto;

import java.util.Map;
import java.util.UUID;

public record CreateRestaurantRequest(
        String restaurantName,
        String streetName,
        String streetNumber,
        String postalCode,
        String city,
        String country,
        String contactEmail,
        String pictureUrl,
        String typeOfCuisine,
        int defaultPreparationTime,
        Map<String, String> openingHours
) {
}
