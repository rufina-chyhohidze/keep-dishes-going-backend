package be.kdg.backend.restaurant.adapter.in.controller;

import be.kdg.backend.restaurant.adapter.in.dto.OwnerDto;
import be.kdg.backend.restaurant.domain.Owner;
import be.kdg.backend.restaurant.domain.Restaurant;
import be.kdg.backend.restaurant.port.out.LoadOwnerPort;
import be.kdg.backend.restaurant.port.out.LoadRestaurantPort;
import be.kdg.backend.restaurant.port.out.SaveOwnerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    private final LoadOwnerPort loadOwnerPort;
    private final SaveOwnerPort saveOwnerPort;
    private final LoadRestaurantPort loadRestaurantPort;

    public OwnerController(LoadOwnerPort loadOwnerPort, SaveOwnerPort saveOwnerPort, LoadRestaurantPort loadRestaurantPort) {
        this.loadOwnerPort = loadOwnerPort;
        this.saveOwnerPort = saveOwnerPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    /**
     * After the user is authenticated with Keycloak, they call this endpoint
     * to create or fetch their Owner record in the local DB.
     */
    @PostMapping("/signup")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<OwnerDto> createOwner(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("given_name");
        UUID keycloakId = UUID.fromString(jwt.getSubject());

        Optional<Owner> existing = loadOwnerPort.loadByEmail(email);
        if (existing.isPresent()) {
            Owner owner = existing.get();
            return ResponseEntity.ok(new OwnerDto(owner.getId(), owner.getEmail(), owner.getName()));
        }

        Owner owner = new Owner(keycloakId, email, name);
        saveOwnerPort.save(owner);
        return ResponseEntity.ok(new OwnerDto(owner.getId(), owner.getEmail(), owner.getName()));
    }

    /**
     * No manual sign-in anymore — Keycloak handles that
     */
    //@PostMapping("/signin")
    //public ResponseEntity<OwnerDto> signin(@RequestBody OwnerSigninRequest request) { ... }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('owner')")
    public Map<String, String> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "sub", jwt.getSubject(),
                "email", jwt.getClaimAsString("email")
        );
    }
    @GetMapping("/me/restaurant")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Restaurant> getRestaurantForCurrentOwner(@AuthenticationPrincipal Jwt jwt) {
        UUID ownerId = UUID.fromString(jwt.getSubject());
        return loadRestaurantPort.loadByOwnerId(ownerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
