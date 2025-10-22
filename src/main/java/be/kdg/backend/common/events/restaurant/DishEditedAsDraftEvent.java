package be.kdg.backend.common.events.restaurant;


import java.util.UUID;

public record DishEditedAsDraftEvent(UUID dishId, UUID restaurantId) {}
