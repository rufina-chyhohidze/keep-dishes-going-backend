package be.kdg.backend.order.adapter.out;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "basket_items",schema = "kdg_order")
public class BasketItemEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID dishId;

    @Column(nullable = false)
    private String dishName;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private BasketJpaEntity basket;

    public UUID getId() {
        return id;
    }

    public UUID getDishId() {
        return dishId;
    }

    public void setDishId(UUID dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BasketJpaEntity getBasket() {
        return basket;
    }

    public void setBasket(BasketJpaEntity basket) {
        this.basket = basket;
    }
}
