package be.kdg.backend.restaurant.adapter.in;

import be.kdg.backend.restaurant.adapter.in.dto.OwnerDto;
import be.kdg.backend.restaurant.adapter.in.dto.OwnerSigninRequest;
import be.kdg.backend.restaurant.adapter.in.dto.OwnerSignupRequest;
import be.kdg.backend.restaurant.port.in.RegisterOwnerCommand;
import be.kdg.backend.restaurant.port.in.RegisterOwnerUseCase;
import be.kdg.backend.restaurant.domain.Owner;
import be.kdg.backend.restaurant.port.in.SignInOwnerCommand;
import be.kdg.backend.restaurant.port.in.SignInOwnerUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final RegisterOwnerUseCase registerOwnerUseCase;
    private final SignInOwnerUseCase signInOwnerUseCase;

    public OwnerController(RegisterOwnerUseCase registerOwner, SignInOwnerUseCase signInOwner) {
        this.registerOwnerUseCase = registerOwner;
        this.signInOwnerUseCase = signInOwner;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody OwnerSignupRequest request) {
        RegisterOwnerCommand command = new RegisterOwnerCommand(
                request.email(), request.password(), request.name());
        this.registerOwnerUseCase.register(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<OwnerDto> signin(@RequestBody OwnerSigninRequest request) {
        SignInOwnerCommand command = new SignInOwnerCommand(request.email(), request.password());

        Owner owner = this.signInOwnerUseCase.signIn(command);

        return ResponseEntity.ok(new OwnerDto(owner.getId(), owner.getEmail(), owner.getName()));
    }
}
