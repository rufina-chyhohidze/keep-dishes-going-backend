package be.kdg.backend.common.events.restaurant;

import be.kdg.backend.restaurant.domain.DishType;

import java.util.UUID;

public record DishEditedAsDraftEvent(UUID dishId, UUID restaurantId) {}
