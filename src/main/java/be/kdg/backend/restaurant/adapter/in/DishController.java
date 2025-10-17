package be.kdg.backend.restaurant.adapter.in;

import be.kdg.backend.restaurant.adapter.in.dto.CreateDishRequest;
import be.kdg.backend.restaurant.adapter.in.dto.DishResponse;
import be.kdg.backend.restaurant.adapter.in.dto.EditDishRequest;
import be.kdg.backend.restaurant.domain.DishType;
import be.kdg.backend.restaurant.domain.FoodTag;
import be.kdg.backend.restaurant.port.in.*;
import be.kdg.backend.restaurant.port.in.request.EditDishCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurants/{restaurantId}/dishes")
public class DishController {
    private static final Logger log = LoggerFactory.getLogger(DishController.class);

    private final EditDishUseCase editDishUseCase;
    private final PublishDishUseCase publishDishUseCase;
    private final LoadDishesByRestaurantUseCase loadDishesByRestaurantUseCase;
    private final UnpublishDishUseCase unpublishDishUseCase;
    private final CreateDishUseCase createDishUseCase;

    public DishController(EditDishUseCase editDishUseCase, PublishDishUseCase publishDishUseCase, LoadDishesByRestaurantUseCase loadDishesByRestaurantUseCase, UnpublishDishUseCase unpublishDishUseCase, CreateDishUseCase createDishUseCase) {
        this.editDishUseCase = editDishUseCase;
        this.publishDishUseCase = publishDishUseCase;
        this.loadDishesByRestaurantUseCase = loadDishesByRestaurantUseCase;
        this.unpublishDishUseCase = unpublishDishUseCase;
        this.createDishUseCase = createDishUseCase;
    }

    @GetMapping
    public ResponseEntity<List<DishResponse>> getDishesByRestaurant(@PathVariable("restaurantId") String rawRestaurantId) {
        log.info("Received restaurantId RAW: '{}'", rawRestaurantId);
        UUID restaurantId = UUID.fromString(rawRestaurantId);
        log.info("Parsed restaurantId OK: {}", restaurantId);

        List<DishResponse> dishes = loadDishesByRestaurantUseCase.loadByRestaurantId(restaurantId)
                .stream()
                .map(DishResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(dishes);
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<List<DishResponse>> getAllDishesForOwner(
            @PathVariable("restaurantId") UUID restaurantId) {

        List<DishResponse> dishes = loadDishesByRestaurantUseCase
                .loadAllByRestaurantId(restaurantId)
                .stream()
                .map(DishResponse::fromDomain)
                .toList();
        log.info("Loaded dishes for the restaurantId: {}", restaurantId);

        return ResponseEntity.ok(dishes);
    }


    @PutMapping("/{dishId}")
    public ResponseEntity<UUID> editDraftDish(@PathVariable UUID restaurantId,
                                              @PathVariable UUID dishId,
                                              @RequestBody EditDishRequest request) {
        log.info("EditDraftDish request: restaurant={}, dish={}, payload={}", restaurantId, dishId, request);


        DishType type = parseDishType(request.type());
        Set<FoodTag> tags = parseFoodTags(request.foodTags());

        var command = new EditDishCommand(
                dishId,
                restaurantId,
                request.name(),
                type,
                tags,
                request.description(),
                request.price(),
                request.pictureUrl()
        );

        UUID updatedId = editDishUseCase.editDish(command);
        return ResponseEntity.ok(updatedId);
    }
    private DishType parseDishType(String raw) {
        try {
            return DishType.valueOf(toEnumConstant(raw));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Unknown dish type: " + raw);
        }
    }

    private Set<FoodTag> parseFoodTags(Iterable<String> rawTags) {
        if (rawTags == null) return EnumSet.noneOf(FoodTag.class);

        Set<FoodTag> tags = EnumSet.noneOf(FoodTag.class);
        for (String raw : rawTags) {
            String normalized = toEnumConstant(raw);
            try {
                tags.add(FoodTag.valueOf(normalized));
            } catch (Exception ex) {
                throw new IllegalArgumentException("Unknown food tag: " + raw);
            }
        }
        return tags;
    }

    @PostMapping("/{dishId}/publish")
    public ResponseEntity<?> publishDish(@PathVariable UUID restaurantId, @PathVariable UUID dishId) {
        log.info("Publishing dish {} for restaurant {}", dishId, restaurantId);
        try {
            publishDishUseCase.publishDish(restaurantId, dishId);
            return ResponseEntity.ok("Dish published successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body("Cannot publish dish: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String toEnumConstant(String input) {
        if (input == null) return null;
        String withUnderscore = input.replaceAll("([a-z])([A-Z])", "$1_$2");
        return withUnderscore.trim()
                .replace('-', ' ')
                .replace('_', ' ')
                .replaceAll("\\s+", "_")
                .toUpperCase();
    }

    @PostMapping("/{dishId}/unpublish")
    public ResponseEntity<?> unpublishDish(@PathVariable UUID restaurantId, @PathVariable UUID dishId) {
        log.info("Unpublishing dish {} for restaurant {}", dishId, restaurantId);
        try {
            unpublishDishUseCase.unpublishDish(restaurantId, dishId);
            return ResponseEntity.ok("Dish unpublished successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body("Cannot unpublish dish: " + e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<UUID> createDish(
            @PathVariable UUID restaurantId,
            @RequestBody CreateDishRequest request) {

        var type = DishType.valueOf(toEnumConstant(request.type()));

        var tags = request.foodTags() == null
                ? Set.<FoodTag>of()
                : request.foodTags().stream()
                .map(tag -> FoodTag.valueOf(toEnumConstant(tag)))
                .collect(Collectors.toSet());

        var command = new CreateDishCommand(
                restaurantId,
                request.name(),
                type,
                tags,
                request.description(),
                request.price(),
                request.pictureUrl()
        );

        log.info("Create dish {} for restaurant {}", command, restaurantId);

        UUID dishId = createDishUseCase.createDish(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(dishId);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadInput(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
