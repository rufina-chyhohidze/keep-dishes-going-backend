package be.kdg.backend.restaurant.adapter.out.owner;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "owners ", schema = "kdg_restaurant")
public class OwnerJpaEntity {
    @Id
    private UUID id;
    private String email;
    private String name;

    protected OwnerJpaEntity() {}

    public OwnerJpaEntity(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
}
