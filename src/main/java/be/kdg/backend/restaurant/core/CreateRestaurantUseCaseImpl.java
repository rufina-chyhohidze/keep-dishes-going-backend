package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Menu;
import be.kdg.backend.restaurant.domain.Restaurant;
import be.kdg.backend.restaurant.port.in.CreateRestaurantUseCase;
import be.kdg.backend.restaurant.port.in.request.CreateRestaurantCommand;
import be.kdg.backend.restaurant.port.out.LoadRestaurantByOwnerId;
import be.kdg.backend.restaurant.port.out.LoadRestaurantPort;
import be.kdg.backend.restaurant.port.out.SaveMenuPort;
import be.kdg.backend.restaurant.port.out.SaveRestaurantPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {

    private Logger logger = LoggerFactory.getLogger(CreateRestaurantUseCaseImpl.class);

    private final SaveRestaurantPort saveRestaurantPort;
    private final SaveMenuPort saveMenuPort;
    private final LoadRestaurantByOwnerId loadRestaurantByOwnerId;
    public CreateRestaurantUseCaseImpl(SaveRestaurantPort saveRestaurantPort, SaveMenuPort saveMenuPort, LoadRestaurantByOwnerId loadRestaurantByOwnerId) {
        this.saveRestaurantPort = saveRestaurantPort;
        this.saveMenuPort = saveMenuPort;
        this.loadRestaurantByOwnerId = loadRestaurantByOwnerId;
    }
    @Override
    public UUID createRestaurant(CreateRestaurantCommand command) {
        loadRestaurantByOwnerId.loadByOwnerId(command.ownerId()).ifPresent(existing -> {
            logger.warn("Owner {} already has a restaurant with ID {}",
                    command.ownerId(), existing.getRestaurantId());
            throw new IllegalArgumentException("Owner already has a restaurant");
        });

        Restaurant restaurant = Restaurant.create(
                command.ownerId(),
                command.name(),
                command.address(),
                command.contactEmail(),
                command.pictureUrl(),
                command.cuisineType(),
                command.defaultPreparationTime(),
                command.openingHours()
        );

        saveRestaurantPort.save(restaurant);

        //create and save the menu automatically
        Menu menu = Menu.create(restaurant.getRestaurantId());
        saveMenuPort.save(menu);
        logger.info("Menu created for restaurant {}", restaurant.getRestaurantId());
        logger.info("Created restaurant '{}' (ID: {}) for owner {}",
                restaurant.getName(),
                restaurant.getRestaurantId(),
                restaurant.getOwnerId()
        );

        return restaurant.getRestaurantId();
    }
}
