package be.kdg.backend.restaurant.adapter.out;

import be.kdg.backend.restaurant.domain.DishAvailability;
import be.kdg.backend.restaurant.domain.StockStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "dishes", schema = "kdg_restaurant")
public class DishJpaEntity {
    @Id
    private UUID dishId;

    private UUID restaurantId;
    private String name;
    private String type;
    private String description;
    private BigDecimal price;
    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    private DishAvailability availability;

    private String foodTags; // comma-separated tags (e.g. "VEGAN,GLUTEN_FREE")

    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus;

    public DishJpaEntity() {}

    public DishJpaEntity(UUID dishId,
                         UUID restaurantId,
                         String name,
                         String type,
                         String description,
                         BigDecimal price,
                         String pictureUrl,
                         DishAvailability availability,
                         StockStatus stockStatus,
                         String foodTags) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.availability = availability;
        this.stockStatus = stockStatus;
        this.foodTags = foodTags;
    }


    public UUID getDishId() { return dishId; }
    public UUID getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getPictureUrl() { return pictureUrl; }
    public DishAvailability getAvailability() { return availability; }
    public StockStatus getStockStatus() { return stockStatus; }
    public String getFoodTags() { return foodTags; }
}