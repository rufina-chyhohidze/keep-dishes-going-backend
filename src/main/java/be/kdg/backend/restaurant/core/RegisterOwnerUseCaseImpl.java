package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Owner;
import be.kdg.backend.restaurant.port.in.RegisterOwnerCommand;
import be.kdg.backend.restaurant.port.in.RegisterOwnerUseCase;
import be.kdg.backend.restaurant.port.out.LoadOwnerPort;
import be.kdg.backend.restaurant.port.out.SaveOwnerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;


//A use case implements an input port registerownerusecase
@Service
public class RegisterOwnerUseCaseImpl implements RegisterOwnerUseCase {
    //Ths use case will always call another output port
    private final LoadOwnerPort loadOwnerPort;
    private final SaveOwnerPort saveOwnerPort;
    private static final Logger log = LoggerFactory.getLogger(RegisterOwnerUseCaseImpl.class);

    public RegisterOwnerUseCaseImpl(LoadOwnerPort loadOwnerPort, SaveOwnerPort saveOwnerPort) {
        this.loadOwnerPort = loadOwnerPort;
        this.saveOwnerPort = saveOwnerPort;
    }
    // you can register with only one email once
    @Override
    public UUID register(RegisterOwnerCommand command) {
        loadOwnerPort.loadByEmail(command.email()).ifPresent(o -> {
            log.warn("Attempt to register with already used email: {}", command.email());
            throw new IllegalArgumentException("Email already in use");
        });


        Owner owner = Owner.register(command.email(), command.password(), command.name());
        saveOwnerPort.save(owner);

        log.info("Successfully registered owner: {} ({})", owner.getName(), owner.getEmail());
        return owner.getId();
    }
}

