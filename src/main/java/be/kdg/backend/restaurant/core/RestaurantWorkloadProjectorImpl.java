package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.RestaurantWorkload;
import be.kdg.backend.restaurant.port.in.RestaurantWorkloadProjector;
import be.kdg.backend.restaurant.port.in.request.IncreaseWorkloadCommand;
import be.kdg.backend.restaurant.port.out.LoadRestaurantWorkloadPort;
import be.kdg.backend.restaurant.port.out.UpdateRestaurantWorkloadPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RestaurantWorkloadProjectorImpl implements RestaurantWorkloadProjector {
    private static final Logger log = LoggerFactory.getLogger(RestaurantWorkloadProjectorImpl.class);

    private final LoadRestaurantWorkloadPort loadPort;
    private final UpdateRestaurantWorkloadPort updatePort;

    public RestaurantWorkloadProjectorImpl(LoadRestaurantWorkloadPort loadPort,
                                           UpdateRestaurantWorkloadPort updatePort) {
        this.loadPort = loadPort;
        this.updatePort = updatePort;
    }

    @Override
    @Transactional
    public void project(IncreaseWorkloadCommand command) {
        RestaurantWorkload workload = loadPort.loadByRestaurantId(command.restaurantId())
                .orElse(new RestaurantWorkload(command.restaurantId(), 0));

        workload.increasePendingOrders();

        RestaurantWorkload updated = updatePort.update(workload);
        log.info("Projected new workload: {}", updated);
    }

}
