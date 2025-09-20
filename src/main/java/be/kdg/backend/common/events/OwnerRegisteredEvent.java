package be.kdg.backend.common.events;

import java.util.UUID;

public record OwnerRegisteredEvent(UUID ownerId, String email) {
}
