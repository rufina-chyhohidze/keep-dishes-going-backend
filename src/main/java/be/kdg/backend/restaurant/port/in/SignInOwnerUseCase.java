package be.kdg.backend.restaurant.port.in;

import be.kdg.backend.restaurant.domain.Owner;

//my webadapter(owner controller) call this input port
//returns domain object owner
public interface SignInOwnerUseCase {
    Owner signIn(SignInOwnerCommand command);
}
