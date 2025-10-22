package be.kdg.backend.restaurant.adapter.out.mapper;

import be.kdg.backend.restaurant.adapter.in.dto.RestaurantDto;
import be.kdg.backend.restaurant.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface RestaurantDtoMapper {

    RestaurantDtoMapper INSTANCE = Mappers.getMapper(RestaurantDtoMapper.class);

    // Domain → DTO
    @Mapping(source = "name", target = "restaurantName")
    @Mapping(source = "cuisineType", target = "cuisineType")
    @Mapping(source = "open", target = "isOpen")
    RestaurantDto toDto(Restaurant restaurant);
}

