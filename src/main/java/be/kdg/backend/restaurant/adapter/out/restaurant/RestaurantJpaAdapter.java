package be.kdg.backend.restaurant.adapter.out.restaurant;

import be.kdg.backend.restaurant.domain.Address;
import be.kdg.backend.restaurant.domain.OpeningHours;
import be.kdg.backend.restaurant.domain.Restaurant;
import be.kdg.backend.restaurant.port.out.LoadRestaurantPort;
import be.kdg.backend.restaurant.port.out.SaveRestaurantPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RestaurantJpaAdapter implements LoadRestaurantPort, SaveRestaurantPort {
    private static final Logger log = LoggerFactory.getLogger(RestaurantJpaAdapter.class);
    private final RestaurantJpaRepository restaurantRepository;

    public RestaurantJpaAdapter(RestaurantJpaRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Optional<Restaurant> loadByOwnerId(UUID ownerId) {
        return restaurantRepository.findByOwnerId(ownerId)
                .map(entity -> new Restaurant(
                        entity.getRestaurantId(),
                        entity.getOwnerId(),
                        entity.getName(),
                        new Address("", "", "", "", ""),
                        entity.getContactEmail(),
                        entity.getPictureUrl(),
                        entity.getTypeOfCuisine(),
                        entity.getDefaultPreparationTime(),
                        new OpeningHours(null)
                ));
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantJpaEntity entity = new RestaurantJpaEntity(
                restaurant.getRestaurantId(),
                restaurant.getOwnerId(),
                restaurant.getName(),
                restaurant.getContactEmail(),
                restaurant.getPictureUrl(),
                restaurant.getCuisineType(),
                restaurant.getDefaultPreparationTime()
        );

        RestaurantJpaEntity saved = restaurantRepository.save(entity);

        log.info("Saved restaurant {} ({})", saved.getName(), saved.getRestaurantId());

        return new Restaurant(
                saved.getRestaurantId(),
                saved.getOwnerId(),
                saved.getName(),
                new Address("", "", "", "", ""),
                saved.getContactEmail(),
                saved.getPictureUrl(),
                saved.getTypeOfCuisine(),
                saved.getDefaultPreparationTime(),
                new OpeningHours(null)
        );
    }

}
