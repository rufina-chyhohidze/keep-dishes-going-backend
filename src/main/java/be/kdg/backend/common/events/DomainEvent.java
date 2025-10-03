package be.kdg.backend.common.events;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime eventPit();
}
