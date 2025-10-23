package be.kdg.backend.restaurant.adapter.in.listeners;

import be.kdg.backend.common.events.OrderAcceptedEvent;
import be.kdg.backend.common.events.OrderReadyEvent;
import be.kdg.backend.common.events.OrderRejectedEvent;
import be.kdg.backend.restaurant.port.in.request.OrderAcceptedProjectionCommand;
import be.kdg.backend.restaurant.port.in.request.OrderReadyProjectionCommand;
import be.kdg.backend.restaurant.port.in.request.OrderRejectedProjectionCommand;
import be.kdg.backend.restaurant.port.in.restaurant_order_projection.RestaurantOrderProjectionProjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * listen → delegate → log
 */
@Component
public class RestaurantOrderProjectionListener {
    private static final Logger log = LoggerFactory.getLogger(RestaurantOrderProjectionListener.class);
    private final RestaurantOrderProjectionProjector projector;

    public RestaurantOrderProjectionListener(RestaurantOrderProjectionProjector projector) {
        this.projector = projector;
    }

    @EventListener(OrderAcceptedEvent.class)
    public void onAccepted(OrderAcceptedEvent event) {
        log.info("Received OrderAcceptedEvent for restaurant {}", event.restaurantId());
        projector.project(new OrderAcceptedProjectionCommand(event.restaurantId()));
    }

    @EventListener(OrderReadyEvent.class)
    public void onReady(OrderReadyEvent event) {
        log.info("Received OrderReadyEvent for restaurant {}", event.restaurantId());
        projector.project(new OrderReadyProjectionCommand(event.restaurantId()));
    }
    @EventListener(OrderRejectedEvent.class)
    public void onRejected(OrderRejectedEvent event) {
        log.info("Received OrderRejectedEvent for restaurant {}", event.restaurantId());
        projector.project(new OrderRejectedProjectionCommand(event.restaurantId()));
    }
}
