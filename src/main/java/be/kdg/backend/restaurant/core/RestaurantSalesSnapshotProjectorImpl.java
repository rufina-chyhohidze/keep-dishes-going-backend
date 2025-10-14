package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.adapter.out.restaurant_sales_snapshot.RestaurantSalesSnapshotEntity;
import be.kdg.backend.restaurant.adapter.out.restaurant_sales_snapshot.RestaurantSalesSnapshotRepository;
import be.kdg.backend.restaurant.port.in.restaurant_sales_snapshot.RestaurantSalesSnapshotCommand;
import be.kdg.backend.restaurant.port.in.restaurant_sales_snapshot.RestaurantSalesSnapshotProjector;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RestaurantSalesSnapshotProjectorImpl implements RestaurantSalesSnapshotProjector {

    private static final Logger log = LoggerFactory.getLogger(RestaurantSalesSnapshotProjectorImpl.class);
    private final RestaurantSalesSnapshotRepository repository;

    public RestaurantSalesSnapshotProjectorImpl(RestaurantSalesSnapshotRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void project(RestaurantSalesSnapshotCommand command) {
        RestaurantSalesSnapshotEntity snapshot = repository.findById(command.restaurantId())
                .orElse(new RestaurantSalesSnapshotEntity(command.restaurantId(), 0.0, 0));

        snapshot.setTotalRevenue(snapshot.getTotalRevenue() + command.orderTotal());
        snapshot.setTotalOrders(snapshot.getTotalOrders() + 1);

        repository.save(snapshot);
        log.info("[Snapshot] Updated for restaurant {}: revenue={}, orders={}",
                command.restaurantId(),
                snapshot.getTotalRevenue(),
                snapshot.getTotalOrders());
    }
}
