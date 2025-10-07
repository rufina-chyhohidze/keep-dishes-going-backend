package be.kdg.backend.restaurant.adapter.out.mapper;

import be.kdg.backend.restaurant.adapter.out.restaurant.RestaurantJpaEntity;
import be.kdg.backend.restaurant.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    // Entity → Domain
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "openingHours", ignore = true)
    @Mapping(source = "typeOfCuisine", target = "cuisineType")
    Restaurant toDomain(RestaurantJpaEntity entity);

    // Domain → Entity
    @Mapping(source = "cuisineType", target = "typeOfCuisine")
    @Mapping(source = "restaurantId", target = "restaurantId")
    @Mapping(source = "ownerId", target = "ownerId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "contactEmail", target = "contactEmail")
    @Mapping(source = "pictureUrl", target = "pictureUrl")
    @Mapping(source = "defaultPreparationTime", target = "defaultPreparationTime")
    RestaurantJpaEntity toEntity(Restaurant restaurant);
}

