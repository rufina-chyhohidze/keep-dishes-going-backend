package be.kdg.backend.restaurant.port.in;

import org.springframework.util.Assert;

import java.util.UUID;

public record RegisterOwnerCommand(UUID id, String email, String name) {

}