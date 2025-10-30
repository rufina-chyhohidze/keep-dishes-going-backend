package be.kdg.backend.restaurant.domain;

import be.kdg.backend.common.events.DishEditedAsDraftEvent;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Dish(UUID dishId,
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


        this.domainEvents.add(new DishEditedAsDraftEvent(this.dishId, this.restaurantId));
    }

    public void publish() {
        if (this.availability != DishAvailability.DRAFT) {
            throw new IllegalStateException("Only draft dishes can be published.");
        }
        this.availability = DishAvailability.PUBLISHED;
    }


    public void unpublish() {
        if (this.availability != DishAvailability.PUBLISHED) {
            throw new IllegalStateException("Only published dishes can be unpublished.");
        }
        this.availability = DishAvailability.UNPUBLISHED;
    }


    public void markOutOfStock() {
        this.stockStatus = StockStatus.OUT_OF_STOCK;
    }

    public void markInStock() {
        this.stockStatus = StockStatus.IN_STOCK;
    }

    public static Set<FoodTag> parseFoodTags(String tagsString) {
        if (tagsString == null || tagsString.isBlank()) return Collections.emptySet();

        return Stream.of(tagsString.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .map(FoodTag::valueOf)
                .collect(Collectors.toSet());
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
