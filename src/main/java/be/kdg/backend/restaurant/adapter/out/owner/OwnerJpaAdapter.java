package be.kdg.backend.restaurant.adapter.out.owner;

import be.kdg.backend.restaurant.domain.Owner;
import be.kdg.backend.restaurant.port.out.LoadOwnerPort;
import be.kdg.backend.restaurant.port.out.SaveOwnerPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OwnerJpaAdapter implements LoadOwnerPort, SaveOwnerPort {
    private final OwnerJpaRepository repo;

    public OwnerJpaAdapter(OwnerJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Owner> loadByEmail(String email) {
        return repo.findByEmail(email)
                .map(e -> new Owner(e.getId(), e.getEmail(), e.getName()));
    }

    @Override
    public void save(Owner owner) {
        OwnerJpaEntity entity = new OwnerJpaEntity(owner.getId(), owner.getEmail(), owner.getName());
        repo.save(entity);
    }
}
