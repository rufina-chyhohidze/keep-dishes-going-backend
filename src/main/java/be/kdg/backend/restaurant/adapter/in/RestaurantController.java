package be.kdg.backend.restaurant.adapter.in;

import be.kdg.backend.restaurant.adapter.in.dto.CreateRestaurantRequest;
import be.kdg.backend.restaurant.domain.Address;
import be.kdg.backend.restaurant.domain.OpeningHours;
import be.kdg.backend.restaurant.domain.Restaurant;
import be.kdg.backend.restaurant.port.in.CreateRestaurantUseCase;
import be.kdg.backend.restaurant.port.in.request.CreateRestaurantCommand;
import be.kdg.backend.restaurant.port.out.LoadRestaurantPort;
import be.kdg.backend.restaurant.port.out.LoadRestaurantWorkloadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final LoadRestaurantWorkloadPort loadRestaurantWorkloadPort;
    private final LoadRestaurantPort loadRestaurantPort;

    Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase, LoadRestaurantWorkloadPort loadRestaurantWorkloadPort, LoadRestaurantPort loadRestaurantPort) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.loadRestaurantWorkloadPort = loadRestaurantWorkloadPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll() {
        List<Restaurant> restaurants = loadRestaurantPort.loadAll();
        logger.info("getting all restaurants: " + restaurants);
        return ResponseEntity.ok(restaurants);
    }
    @PostMapping
    public ResponseEntity<UUID> createRestaurant(
            @RequestBody CreateRestaurantRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        if (jwt == null) {
            return ResponseEntity.status(401).build();
        }
        // Extract owner ID from token claims
        String ownerId = jwt.getSubject();

        Address address = new Address(
                request.streetName(),
                request.streetNumber(),
                request.postalCode(),
                request.city(),
                request.country()
        );

        OpeningHours openingHours = new OpeningHours(request.openingHours());

        CreateRestaurantCommand command = new CreateRestaurantCommand(
                UUID.fromString(ownerId),
                request.restaurantName(),
                address,
                request.contactEmail(),
                request.pictureUrl(),
                request.typeOfCuisine(),
                request.defaultPreparationTime(),
                openingHours
        );

        logger.info("Received CreateRestaurantRequest: {} by owner {}", request, ownerId);
        UUID restaurantId = createRestaurantUseCase.createRestaurant(command);
        return ResponseEntity.ok(restaurantId);
    }
    @GetMapping("/workload/{restaurantId}")
    public ResponseEntity<Integer> getWorkload(@PathVariable UUID restaurantId) {
        return loadRestaurantWorkloadPort.loadByRestaurantId(restaurantId)
                .map(workload -> ResponseEntity.ok(workload.getPendingOrders()))
                .orElse(ResponseEntity.notFound().build());
    }
}
