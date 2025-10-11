package be.kdg.backend.order.adapter.out;

import be.kdg.backend.order.domain.BasketStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "baskets",schema = "kdg_order")
public class BasketJpaEntity {
    @Id
    private UUID basketId;

    @Column(nullable = false)
    private UUID restaurantId;

    @Enumerated(EnumType.STRING)
    private BasketStatus status;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BasketItemEntity> items = new ArrayList<>();

    public UUID getBasketId() {
        return basketId;
    }

    public void setBasketId(UUID basketId) {
        this.basketId = basketId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BasketStatus getStatus() {
        return status;
    }

    public void setStatus(BasketStatus status) {
        this.status = status;
    }

    public List<BasketItemEntity> getItems() {
        return items;
    }

    public void setItems(List<BasketItemEntity> items) {
        this.items = items;
    }
}
