package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Menu;

public interface SaveMenuPort {
    void save(Menu menu);
}
