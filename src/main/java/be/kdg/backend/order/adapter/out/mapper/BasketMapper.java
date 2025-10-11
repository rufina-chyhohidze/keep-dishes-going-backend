package be.kdg.backend.order.adapter.out.mapper;

import be.kdg.backend.order.adapter.out.BasketItemEntity;
import be.kdg.backend.order.adapter.out.BasketJpaEntity;
import be.kdg.backend.order.domain.Basket;
import be.kdg.backend.order.domain.BasketItem;

import java.util.List;
import java.util.stream.Collectors;

public class BasketMapper {
    public static Basket toDomain(BasketJpaEntity entity) {
        List<BasketItem> items = entity.getItems().stream()
                .map(i -> new BasketItem(i.getDishId(), i.getDishName(), i.getUnitPrice(), i.getQuantity()))
                .collect(Collectors.toList());
        return new Basket(entity.getBasketId(), entity.getRestaurantId(), items, entity.getStatus());
    }

    public static BasketJpaEntity toEntity(Basket basket) {
        BasketJpaEntity entity = new BasketJpaEntity();
        entity.setBasketId(basket.getBasketId());
        entity.setRestaurantId(basket.getRestaurantId());
        entity.setStatus(basket.getStatus());

        List<BasketItemEntity> itemEntities = basket.getItems().stream().map(item -> {
            BasketItemEntity e = new BasketItemEntity();
            e.setDishId(item.getDishId());
            e.setDishName(item.getDishName());
            e.setUnitPrice(item.getUnitPrice());
            e.setQuantity(item.getQuantity());
            e.setBasket(entity);
            return e;
        }).collect(Collectors.toList());

        entity.setItems(itemEntities);
        return entity;
    }
}
