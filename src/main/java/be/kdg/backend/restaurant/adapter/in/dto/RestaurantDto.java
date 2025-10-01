package be.kdg.backend.restaurant.adapter.in.dto;

import be.kdg.backend.restaurant.domain.Address;
import be.kdg.backend.restaurant.domain.OpeningHours;

import java.util.UUID;

public record RestaurantDto (UUID restaurantId,
                             UUID ownerId,
                             String restaurantName,
                             Address address,
                             String contactEmail,
                             String pictureUrl,
                             String typeOfCuisine,
                             int defaultPreparationTime,
                             OpeningHours openingHours){
}
