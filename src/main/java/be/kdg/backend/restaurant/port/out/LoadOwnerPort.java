package be.kdg.backend.restaurant.port.out;

import be.kdg.backend.restaurant.domain.Owner;
import java.util.Optional;

public interface LoadOwnerPort {
    Optional<Owner> loadByEmail(String email);
}