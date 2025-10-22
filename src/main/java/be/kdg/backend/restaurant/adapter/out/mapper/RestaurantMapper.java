package be.kdg.backend.restaurant.adapter.out.mapper;

import be.kdg.backend.restaurant.adapter.out.restaurant.RestaurantJpaEntity;
import be.kdg.backend.restaurant.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(source = "typeOfCuisine", target = "cuisineType")
    @Mapping(source = "open", target = "open")
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "openingHours", ignore = true)
    Restaurant toDomain(RestaurantJpaEntity entity);

    @Mapping(source = "cuisineType", target = "typeOfCuisine")
    @Mapping(source = "open", target = "isOpen")
    RestaurantJpaEntity toEntity(Restaurant restaurant);
}


