package be.kdg.backend.restaurant.adapter.in;

import be.kdg.backend.restaurant.adapter.in.dto.EditDishRequest;
import be.kdg.backend.restaurant.domain.DishType;
import be.kdg.backend.restaurant.domain.FoodTag;
import be.kdg.backend.restaurant.port.in.EditDishUseCase;
import be.kdg.backend.restaurant.port.in.request.EditDishCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}/dishes")
public class DishController {
    private static final Logger log = LoggerFactory.getLogger(DishController.class);

    private final EditDishUseCase editDishUseCase;

    public DishController(EditDishUseCase editDishUseCase) {
        this.editDishUseCase = editDishUseCase;
    }
    @PutMapping("/{dishId}")
    public ResponseEntity<UUID> editDraftDish(@PathVariable UUID restaurantId,
                                              @PathVariable UUID dishId,
                                              @RequestBody EditDishRequest request) {
        log.info("EditDraftDish request: restaurant={}, dish={}, payload={}", restaurantId, dishId, request);

        // robust enum parsing
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


    private String toEnumConstant(String input) {
        if (input == null) return null;
        String withUnderscore = input.replaceAll("([a-z])([A-Z])", "$1_$2");
        return withUnderscore.trim()
                .replace('-', ' ')
                .replace('_', ' ')
                .replaceAll("\\s+", "_")
                .toUpperCase();
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadInput(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        // e.g., trying to edit a non-DRAFT dish
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
