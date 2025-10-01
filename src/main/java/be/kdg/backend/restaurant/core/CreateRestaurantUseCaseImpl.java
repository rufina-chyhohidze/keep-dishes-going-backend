package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Restaurant;
import be.kdg.backend.restaurant.port.in.CreateRestaurantUseCase;
import be.kdg.backend.restaurant.port.in.request.CreateRestaurantCommand;
import be.kdg.backend.restaurant.port.out.LoadRestaurantPort;
import be.kdg.backend.restaurant.port.out.SaveRestaurantPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {

    private Logger logger = LoggerFactory.getLogger(CreateRestaurantUseCaseImpl.class);

    private final LoadRestaurantPort loadRestaurantPort;
    private final SaveRestaurantPort saveRestaurantPort;

    public CreateRestaurantUseCaseImpl(LoadRestaurantPort loadRestaurantPort, SaveRestaurantPort saveRestaurantPort) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.saveRestaurantPort = saveRestaurantPort;
    }

    @Override
    public UUID createRestaurant(CreateRestaurantCommand command) {
        loadRestaurantPort.loadByOwnerId(command.ownerId()).ifPresent(r -> {
            logger.warn("Owner {} already has a restaurant", command.ownerId());
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

        logger .info("Created restaurant {} for owner {}", restaurant.getName(), restaurant.getOwnerId());
        return restaurant.getRestaurantId();
    }
}
