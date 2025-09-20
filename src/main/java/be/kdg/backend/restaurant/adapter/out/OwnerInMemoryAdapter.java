package be.kdg.backend.restaurant.adapter.out;

import be.kdg.backend.restaurant.domain.Owner;
import be.kdg.backend.restaurant.port.out.LoadOwnerPort;
import be.kdg.backend.restaurant.port.out.SaveOwnerPort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class OwnerInMemoryAdapter implements LoadOwnerPort, SaveOwnerPort {
    private final Map<String, Owner> storage = new HashMap<>();

    @Override
    public Optional<Owner> loadByEmail(String email) {
        return Optional.ofNullable(storage.get(email));
    }

    @Override
    public void save(Owner owner) {
        storage.put(owner.getEmail(), owner);
    }
}
