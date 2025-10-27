package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.adapter.out.restaurant_order_projection.RestaurantOrderProjectionJpaEntity;
import be.kdg.backend.restaurant.adapter.out.restaurant_order_projection.RestaurantOrderProjectionRepository;
import be.kdg.backend.restaurant.port.in.request.OrderAcceptedProjectionCommand;
import be.kdg.backend.restaurant.port.in.request.OrderReadyProjectionCommand;
import be.kdg.backend.restaurant.port.in.request.OrderRejectedProjectionCommand;
import be.kdg.backend.restaurant.port.in.restaurant_order_projection.RestaurantOrderProjectionProjector;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RestaurantOrderProjectionProjectorImpl implements RestaurantOrderProjectionProjector {
    private static final Logger log = LoggerFactory.getLogger(RestaurantOrderProjectionProjectorImpl.class);
    private final RestaurantOrderProjectionRepository repo;

    public RestaurantOrderProjectionProjectorImpl(RestaurantOrderProjectionRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void project(OrderAcceptedProjectionCommand command) {
        var projection = repo.findByRestaurantId(command.restaurantId())
                .orElseGet(() -> new RestaurantOrderProjectionJpaEntity(command.restaurantId(), 0, 0,0));

        projection.incrementAccepted();
        repo.save(projection);

        log.info("Accepted order projected for restaurant {}", command.restaurantId());
    }

    @Override
    @Transactional
    public void project(OrderReadyProjectionCommand command) {
        var projection = repo.findByRestaurantId(command.restaurantId())
                .orElseThrow(() -> projectionNotFound(command.restaurantId()));

        projection.moveAcceptedToReady();
        repo.save(projection);

        log.info("Order ready projected for restaurant {}", command.restaurantId());
    }

    @Override
    @Transactional
    public void project(OrderRejectedProjectionCommand command) {
        var optionalProjection = repo.findByRestaurantId(command.restaurantId());
        if (optionalProjection.isEmpty()) {
            log.warn("Projection not found for restaurant {}", command.restaurantId());
            return;
        }

        var projection = optionalProjection.get();

        projection.moveAcceptedToRejected();
        repo.save(projection);

        log.info("Order rejected projected for restaurant {}", command.restaurantId());
    }

    private RuntimeException projectionNotFound(Object restaurantId) {
        log.error("Projection not found for restaurant {}", restaurantId);
        return new IllegalStateException("Projection not found for restaurant " + restaurantId);
    }
}

