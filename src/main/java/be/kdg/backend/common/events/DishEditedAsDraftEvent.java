package be.kdg.backend.common.events;


import java.util.UUID;

public record DishEditedAsDraftEvent(UUID dishId, UUID restaurantId) {}
