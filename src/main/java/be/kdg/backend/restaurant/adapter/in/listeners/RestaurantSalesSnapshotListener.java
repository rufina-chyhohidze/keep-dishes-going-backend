package be.kdg.backend.restaurant.adapter.in.listeners;

import be.kdg.backend.common.events.OrderPlacedEvent;
import be.kdg.backend.restaurant.port.in.restaurant_sales_snapshot.RestaurantSalesSnapshotCommand;
import be.kdg.backend.restaurant.port.in.restaurant_sales_snapshot.RestaurantSalesSnapshotProjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RestaurantSalesSnapshotListener {
    private static final Logger log = LoggerFactory.getLogger(RestaurantSalesSnapshotListener.class);
    private final RestaurantSalesSnapshotProjector projector;

    public RestaurantSalesSnapshotListener(RestaurantSalesSnapshotProjector projector) {
        this.projector = projector;
    }

    @EventListener(OrderPlacedEvent.class)
    public void onOrderPlaced(OrderPlacedEvent event) {
        log.info("[Snapshot] Received OrderPlacedEvent for restaurant {}", event.restaurantId());
        projector.project(new RestaurantSalesSnapshotCommand(
                event.restaurantId(),
                event.totalPrice()
        ));
    }
}
