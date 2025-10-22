package be.kdg.backend.restaurant.adapter.out.mapper;

import be.kdg.backend.restaurant.adapter.out.menu.MenuJpaEntity;
import be.kdg.backend.restaurant.domain.Menu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MenuMapper {
    public Menu toDomain(MenuJpaEntity entity) {
        return new Menu(entity.getMenuId(), entity.getRestaurantId(), new ArrayList<>());
    }

    public MenuJpaEntity toEntity(Menu menu) {
        return new MenuJpaEntity(menu.getMenuId(), menu.getRestaurantId());
    }
}
