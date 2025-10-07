package be.kdg.backend.restaurant.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
//agregate root
public class Menu {
    private final UUID menuId;
    private final UUID restaurantId;
    private final List<Dish> dishes;

    private static final int MAX_PUBLISHED_DISHES = 10;

    public Menu(UUID menuId, UUID restaurantId, List<Dish> dishes) {
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.dishes = new ArrayList<>(dishes);
    }

    public static Menu create(UUID restaurantId) {
        return new Menu(UUID.randomUUID(), restaurantId, new ArrayList<>());
    }

    public UUID getMenuId() {
        return menuId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public List<Dish> getDishes() {
        return Collections.unmodifiableList(dishes);
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }

    public void publishDish(UUID dishId) {
        Dish dish = findDishById(dishId);
        long publishedCount = dishes.stream()
                .filter(d -> d.getAvailability() == DishAvailability.PUBLISHED)
                .count();

        if (dish.getAvailability() != DishAvailability.DRAFT) {
            throw new IllegalStateException("Only draft dishes can be published.");
        }

        if (publishedCount >= MAX_PUBLISHED_DISHES) {
            throw new IllegalStateException("Cannot publish more than 10 dishes.");
        }

        dish.publish();
    }

    private Dish findDishById(UUID dishId) {
        return dishes.stream()
                .filter(d -> d.getDishId().equals(dishId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Dish not found: " + dishId));
    }
}
