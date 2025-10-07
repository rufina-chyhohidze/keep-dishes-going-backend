package be.kdg.backend.restaurant.adapter.out.restaurant;

import be.kdg.backend.restaurant.adapter.out.mapper.RestaurantMapper;
import be.kdg.backend.restaurant.domain.Restaurant;
import be.kdg.backend.restaurant.port.out.LoadRestaurantPort;
import be.kdg.backend.restaurant.port.out.SaveRestaurantPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RestaurantJpaAdapter implements LoadRestaurantPort, SaveRestaurantPort {
    private static final Logger log = LoggerFactory.getLogger(RestaurantJpaAdapter.class);

    private final RestaurantJpaRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantJpaAdapter(RestaurantJpaRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public Optional<Restaurant> loadByOwnerId(UUID ownerId) {
        return restaurantRepository.findByOwnerId(ownerId)
                .map(restaurantMapper::toDomain);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantJpaEntity entity = restaurantMapper.toEntity(restaurant);
        RestaurantJpaEntity saved = restaurantRepository.save(entity);

        log.info("Saved restaurant {} ({})", saved.getName(), saved.getRestaurantId());
        return restaurantMapper.toDomain(saved);
    }

    @Override
    public List<Restaurant> loadAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDomain)
                .collect(Collectors.toList());
    }
}
