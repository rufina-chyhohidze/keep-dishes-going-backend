package be.kdg.backend.restaurant.adapter.in.controller;

import be.kdg.backend.restaurant.adapter.in.requests.CreateRestaurantRequest;
import be.kdg.backend.restaurant.adapter.in.dto.RestaurantDto;
import be.kdg.backend.restaurant.adapter.out.mapper.RestaurantDtoMapper;
import be.kdg.backend.restaurant.domain.Address;
import be.kdg.backend.restaurant.domain.OpeningHours;
import be.kdg.backend.restaurant.domain.Restaurant;
import be.kdg.backend.restaurant.port.in.CreateRestaurantUseCase;
import be.kdg.backend.restaurant.port.in.ToggleRestaurantOpenStatusUseCase;
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
    private final ToggleRestaurantOpenStatusUseCase toggleRestaurantOpenStatusUseCase;
    private final RestaurantDtoMapper restaurantDtoMapper;

    Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase, LoadRestaurantWorkloadPort loadRestaurantWorkloadPort, LoadRestaurantPort loadRestaurantPort, ToggleRestaurantOpenStatusUseCase toggleRestaurantOpenStatusUseCase, RestaurantDtoMapper restaurantDtoMapper) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.loadRestaurantWorkloadPort = loadRestaurantWorkloadPort;
        this.loadRestaurantPort = loadRestaurantPort;
        this.toggleRestaurantOpenStatusUseCase = toggleRestaurantOpenStatusUseCase;
        this.restaurantDtoMapper = restaurantDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll() {
        List<Restaurant> restaurants = loadRestaurantPort.loadAll();
        logger.debug("getting all restaurants: " + restaurants);
        return ResponseEntity.ok(restaurants);
    }
    @PostMapping
    public ResponseEntity<UUID> createRestaurant(
            @RequestBody CreateRestaurantRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        if (jwt == null) {
            return ResponseEntity.status(401).build();
        }
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

    @PutMapping("/{restaurantId}/toggle")
    public ResponseEntity<RestaurantDto> toggleOpen(
            @PathVariable UUID restaurantId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        if (jwt == null) {
            return ResponseEntity.status(401).build();
        }

        UUID ownerId = UUID.fromString(jwt.getSubject());
        var updated = toggleRestaurantOpenStatusUseCase.toggleOpenStatus(restaurantId, ownerId);

        var dto = restaurantDtoMapper.toDto(updated);
        return ResponseEntity.ok(dto);
    }

}
