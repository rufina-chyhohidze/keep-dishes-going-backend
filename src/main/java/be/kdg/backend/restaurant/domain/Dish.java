package be.kdg.backend.restaurant.domain;

import be.kdg.backend.common.events.restaurant.DishEditedAsDraftEvent;

import java.math.BigDecimal;
import java.util.*;

public class Dish {
    private final UUID dishId;
    private final UUID restaurantId;

    private String name;
    private DishType type;
    private Set<FoodTag> foodTags;
    private String description;
    private BigDecimal price;
    private String pictureUrl;

    private DishAvailability availability;
    private StockStatus stockStatus;

    private final List<Object> domainEvents = new ArrayList<>();

    private Dish(UUID dishId,
                 UUID restaurantId,
                 String name,
                 DishType type,
                 Set<FoodTag> foodTags,
                 String description,
                 BigDecimal price,
                 String pictureUrl,
                 DishAvailability availability,
                 StockStatus stockStatus) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.type = type;
        this.foodTags = (foodTags == null ? new HashSet<>() : EnumSet.copyOf(foodTags));
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.availability = availability;
        this.stockStatus = stockStatus;
    }

    public static Dish draft(UUID restaurantId,
                             String name,
                             DishType type,
                             Set<FoodTag> foodTags,
                             String description,
                             BigDecimal price,
                             String pictureUrl) {
        return new Dish(
                UUID.randomUUID(),
                restaurantId,
                name,
                type,
                foodTags,
                description,
                price,
                pictureUrl,
                DishAvailability.DRAFT,
                StockStatus.IN_STOCK
        );
    }
    public static Dish rehydrate(UUID dishId, UUID restaurantId, String name, DishType type,
                                 Set<FoodTag> foodTags, String description, BigDecimal price,
                                 String pictureUrl, DishAvailability dishAvailability, StockStatus stockStatus) {
        return new Dish(dishId, restaurantId, name, type, foodTags, description, price, pictureUrl, dishAvailability, stockStatus);
    }


    /** Edits are only allowed while in DRAFT to avoid impacting the live menu. */
    public void editDraft(String name,
                          DishType type,
                          Set<FoodTag> foodTags,
                          String description,
                          BigDecimal price,
                          String pictureUrl) {
        if (this.availability != DishAvailability.DRAFT) {
            throw new IllegalStateException("Can only edit a dish while it is in DRAFT.");
        }
        this.name = name;
        this.type = type;
        this.foodTags = (foodTags == null ? new HashSet<>() : EnumSet.copyOf(foodTags));
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;

        // domain event to record the fact
        this.domainEvents.add(new DishEditedAsDraftEvent(this.dishId, this.restaurantId));
    }


    public UUID getDishId() { return dishId; }
    public UUID getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public DishType getType() { return type; }
    public Set<FoodTag> getFoodTags() { return Collections.unmodifiableSet(foodTags); }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getPictureUrl() { return pictureUrl; }
    public DishAvailability getAvailability() { return availability; }
    public StockStatus getStockStatus() { return stockStatus; }
    public List<Object> getDomainEvents() { return List.copyOf(domainEvents); }
}
