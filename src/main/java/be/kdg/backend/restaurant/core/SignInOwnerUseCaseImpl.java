package be.kdg.backend.restaurant.core;

import be.kdg.backend.restaurant.domain.Owner;
import be.kdg.backend.restaurant.port.in.SignInOwnerCommand;
import be.kdg.backend.restaurant.port.in.SignInOwnerUseCase;
import be.kdg.backend.restaurant.port.out.LoadOwnerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

//A use case implements an input port signinownerusecase
//depends only on ports
@Service
public class SignInOwnerUseCaseImpl implements SignInOwnerUseCase {
    private final LoadOwnerPort loadOwnerPort;
    private static final Logger log = LoggerFactory.getLogger(SignInOwnerUseCaseImpl.class);

    //loads user by email
    public SignInOwnerUseCaseImpl(LoadOwnerPort loadOwnerPort) {
        this.loadOwnerPort = loadOwnerPort;
    }

    @Override
    public Owner signIn(SignInOwnerCommand command) {
        return loadOwnerPort.loadByEmail(command.email())
                .filter(o -> o.getPassword().equals(command.password()))
                .map(owner -> {
                    log.info("Owner successfully signed in: {} ({})", owner.getName(), owner.getEmail());
                    return owner;
                })
                .orElseThrow(() -> {
                    log.warn("Failed sign-in attempt for email: {}", command.email());
                    return new RuntimeException("Invalid credentials");
                });
    }

}
