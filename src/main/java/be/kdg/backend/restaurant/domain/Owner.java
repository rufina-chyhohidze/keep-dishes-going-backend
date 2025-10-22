package be.kdg.backend.restaurant.domain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Owner {
    private final UUID id;
    private final String email;
    private final String name;

    private final List<Object> domainEvents = new ArrayList<>();

    public Owner(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Owner{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
