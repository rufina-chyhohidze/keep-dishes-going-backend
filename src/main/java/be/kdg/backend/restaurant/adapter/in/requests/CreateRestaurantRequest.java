package be.kdg.backend.restaurant.adapter.in.requests;

import java.util.Map;

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
