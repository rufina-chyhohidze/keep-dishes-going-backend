package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.RestaurantWorkload;

public interface UpdateRestaurantWorkloadPort {
    RestaurantWorkload update(RestaurantWorkload workload);
}
