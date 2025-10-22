package be.kdg.backend.restaurant.adapter.out.menu;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "menu", schema = "kdg_restaurant")
public class MenuJpaEntity {
    @Id
    private UUID menuId;

    private UUID restaurantId;

    protected MenuJpaEntity() {
    }

    public MenuJpaEntity(UUID menuId, UUID restaurantId) {
        this.menuId = menuId;
        this.restaurantId = restaurantId;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setMenuId(UUID menuId) {
        this.menuId = menuId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}
