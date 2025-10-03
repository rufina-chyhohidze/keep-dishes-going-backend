package be.kdg.backend.restaurant.adapter.in;

import be.kdg.backend.common.events.OrderPlacedEvent;
import be.kdg.backend.restaurant.port.in.RestaurantWorkloadProjector;
import be.kdg.backend.restaurant.port.in.request.IncreaseWorkloadCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RestaurantWorkloadListener {
    private static final Logger log = LoggerFactory.getLogger(RestaurantWorkloadListener.class);

    private final RestaurantWorkloadProjector projector;

    public RestaurantWorkloadListener(RestaurantWorkloadProjector projector) {
        this.projector = projector;
    }

    @EventListener(OrderPlacedEvent.class)
    public void onOrderPlaced(OrderPlacedEvent event) {
        log.info("Received OrderPlacedEvent for restaurant {}", event.restaurantId());

        projector.project(new IncreaseWorkloadCommand(event.restaurantId()));
    }
}

