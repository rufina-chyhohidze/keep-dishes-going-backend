package be.kdg.backend.restaurant.adapter.in.dto;

import java.util.UUID;

public record OwnerDto(UUID id, String email, String name) {

}