package be.kdg.backend.order.port.in.request;

import java.util.UUID;

public record UpdateItemQuantityCommand( UUID basketId,
                                         UUID dishId,
                                         int quantity) {
}
