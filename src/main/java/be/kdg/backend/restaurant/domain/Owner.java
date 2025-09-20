package be.kdg.backend.restaurant.domain;

import be.kdg.backend.common.events.OwnerRegisteredEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Owner {
    private final UUID id;
    private final String email;
    private final String password;
    private final String name;

    private final List<Object> domainEvents = new ArrayList<>();

    private Owner(UUID id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static Owner register(String email, String password, String name) {
        Owner owner = new Owner(UUID.randomUUID(), email, password, name);
        owner.domainEvents.add(new OwnerRegisteredEvent(owner.id, owner.email));
        return owner;
    }

 //   public boolean checkPassword(String rawPassword) {
   //     return this.password.equals(rawPassword);
    //}

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public List<Object> getDomainEvents() { return Collections.unmodifiableList(domainEvents); }

    @Override
    public String toString() {
        return "Owner{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
