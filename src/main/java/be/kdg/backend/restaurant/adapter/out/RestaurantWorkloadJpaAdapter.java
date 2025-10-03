package be.kdg.backend.restaurant.adapter.out;

import be.kdg.backend.restaurant.domain.RestaurantWorkload;
import be.kdg.backend.restaurant.port.out.LoadRestaurantWorkloadPort;
import be.kdg.backend.restaurant.port.out.UpdateRestaurantWorkloadPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RestaurantWorkloadJpaAdapter implements LoadRestaurantWorkloadPort, UpdateRestaurantWorkloadPort {
    private final RestaurantWorkloadJpaRepository repo;

    public RestaurantWorkloadJpaAdapter(RestaurantWorkloadJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<RestaurantWorkload> loadByRestaurantId(UUID restaurantId) {
        return repo.findById(restaurantId)
                .map(e -> new RestaurantWorkload(e.getRestaurantId(), e.getPendingOrders()));
    }



    @Override
    public RestaurantWorkload update(RestaurantWorkload workload) {
        RestaurantWorkloadJpaEntity entity =
                new RestaurantWorkloadJpaEntity(workload.getRestaurantId(), workload.getPendingOrders());
        RestaurantWorkloadJpaEntity saved = repo.save(entity);
        return new RestaurantWorkload(saved.getRestaurantId(), saved.getPendingOrders());
    }
}
