package be.kdg.backend.restaurant.port.in;

import be.kdg.backend.restaurant.domain.Owner;

import java.util.UUID;

//my webadapter(owner controller) call this input port
//return domain object owner
public interface RegisterOwnerUseCase {
    UUID register(RegisterOwnerCommand command);

}
