package be.kdg.backend.restaurant.domain;

import java.util.UUID;

public class Restaurant {
    private final UUID restaurantId;
    private final UUID ownerId;
    private final String name;
    private final Address address;
    private final String contactEmail;
    private final String pictureUrl;
    private final String cuisineType;
    private final int defaultPreparationTime; // in minutes
    private final OpeningHours openingHours;

    private Restaurant(UUID restaurantId, UUID ownerId, String name, Address address,
                       String contactEmail, String pictureUrl, String cuisineType,
                       int defaultPreparationTime, OpeningHours openingHours) {
        this.restaurantId = restaurantId;
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
        this.pictureUrl = pictureUrl;
        this.cuisineType = cuisineType;
        this.defaultPreparationTime = defaultPreparationTime;
        this.openingHours = openingHours;
    }

    public static Restaurant create(UUID ownerId, String name, Address address, String contactEmail,
                                    String pictureUrl, String cuisineType, int defaultPreparationTime,
                                    OpeningHours openingHours) {
        return new Restaurant(UUID.randomUUID(), ownerId, name, address, contactEmail, pictureUrl, cuisineType, defaultPreparationTime, openingHours);
    }

    public UUID getRestaurantId() { return restaurantId; }
    public UUID getOwnerId() { return ownerId; }
    public String getName() { return name; }
    public Address getAddress() { return address; }
    public String getContactEmail() { return contactEmail; }
    public String getPictureUrl() { return pictureUrl; }
    public String getCuisineType() { return cuisineType; }
    public int getDefaultPreparationTime() { return defaultPreparationTime; }
    public OpeningHours getOpeningHours() { return openingHours; }
}
