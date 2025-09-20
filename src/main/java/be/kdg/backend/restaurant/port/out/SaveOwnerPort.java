package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Owner;

public interface SaveOwnerPort {
    void save(Owner owner);
}