package be.kdg.backend.order.port.out;

import be.kdg.backend.order.domain.Basket;

import java.util.Optional;
import java.util.UUID;

public interface LoadBasketPort {
    Optional<Basket> load(UUID basketId);
}
