package be.kdg.backend.restaurant.adapter.out.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "restaurants", schema = "kdg_restaurant")
public class RestaurantJpaEntity {

    @Id
    private UUID restaurantId;
    private UUID ownerId;
    private String name;
    private String contactEmail;
    private String pictureUrl;
    private String typeOfCuisine;
    private int defaultPreparationTime;

    protected RestaurantJpaEntity() {}

    public RestaurantJpaEntity(UUID restaurantId, UUID ownerId, String name,
                               String contactEmail, String pictureUrl,
                               String typeOfCuisine, int defaultPreparationTime) {
        this.restaurantId = restaurantId;
        this.ownerId = ownerId;
        this.name = name;
        this.contactEmail = contactEmail;
        this.pictureUrl = pictureUrl;
        this.typeOfCuisine = typeOfCuisine;
        this.defaultPreparationTime = defaultPreparationTime;
    }

    public UUID getRestaurantId() { return restaurantId; }
    public UUID getOwnerId() { return ownerId; }
    public String getName() { return name; }
    public String getContactEmail() { return contactEmail; }
    public String getPictureUrl() { return pictureUrl; }
    public String getTypeOfCuisine() { return typeOfCuisine; }
    public int getDefaultPreparationTime() { return defaultPreparationTime; }
}
